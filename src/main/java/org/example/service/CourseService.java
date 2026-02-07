package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.example.entity.Course;
import org.example.entity.Instructor;
import org.example.entity.User;
import org.example.enumuration.Permission;
import org.example.repository.CourseRepo;
import org.example.repository.UserRepo;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class CourseService {
    private final CourseRepo courseRepo;
    private final UserRepo userRepo;

    public CourseService(CourseRepo courseRepo , UserRepo userRepo){
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;

    }

    public void createCourse(User caller, String title, BigDecimal price, Long instructorId){
        AuthorizationService.authorize(caller, Permission.CREATE_COURSE);
        Instructor instructor = userRepo.findInstructorById(instructorId)
                .orElseThrow(()->new IllegalArgumentException("Instructor is not exist"));

        Course course = new Course(title,price,instructor);
        courseRepo.addCourse(course);
        log.info("new course added {}",course.getTitle());
    }

    public void deleteCourse(User caller,Long courseId){
        AuthorizationService.authorize(caller, Permission.DELETE_COURSE);
        Course course = courseRepo.findCourseById(courseId)
                .orElseThrow(()->new IllegalArgumentException("Course is not Exist"));
        if(!course.getEnrollments().isEmpty()){
            throw new IllegalStateException("Course has active enrollments");
        }
        courseRepo.deleteCourseById(courseId);
        log.info("Course {} deleted",course.getTitle());
    }

    public List<Course> getAllCourses(User caller){
        AuthorizationService.authorize(caller, Permission.VIEW_ALL_COURSES);
        return courseRepo.getAllCourses();
    }

    public List<Course> getCourseByInstructor(User caller){
        AuthorizationService.authorize(caller, Permission.VIEW_INSTRUCTOR_COURSES);
       return courseRepo.getCoursesByInstructor(caller.getId());
    }


}
