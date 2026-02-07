package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Course;
import org.example.entity.Enrollment;
import org.example.entity.Student;
import org.example.entity.User;
import org.example.enumuration.Permission;
import org.example.repository.CourseRepo;
import org.example.repository.EnrollmentRepo;
import org.example.repository.UserRepo;

import java.util.List;

@Slf4j
public class GradeService {
    private final CourseRepo courseRepo;
    private final UserRepo userRepo;
    private final EnrollmentRepo enrollmentRepo;

    public GradeService(CourseRepo courseRepo , UserRepo userRepo,EnrollmentRepo enrollmentRepo){
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
        this.enrollmentRepo = enrollmentRepo;

    }

    public void assignGrade(User instructor,Long studentId , Long courseId , double grade){
       AuthorizationService.authorize(instructor, Permission.ASSIGN_GRADE);
        Student student = userRepo.findStudentById(studentId)
                .orElseThrow(()-> new IllegalArgumentException("Student is not exist"));

        Course course = courseRepo.findCourseById(courseId)
                .orElseThrow(()-> new IllegalArgumentException("course is not exist"));

        if(!course.getInstructor().getId().equals(instructor.getId())){
            throw new SecurityException("you are not instructor of this course");
        }
        Enrollment enrollment = enrollmentRepo.findEnrollment(studentId,courseId)
                .orElseThrow(()-> new IllegalStateException("student is not enrolled in this course"));
        enrollment.setGrade(grade);
        log.info("Grade {} has been entered for student {} in course {} by {}",
                grade, student.getName(), course.getTitle(), instructor.getName());

    }

    public void showStudentGrades(User student) {
        AuthorizationService.authorize(student, Permission.VIEW_STUDENT_GRADES);
        List<Enrollment> enrollments = enrollmentRepo.getEnrollmentsForStudent(student.getId());
        log.info("Grades for student {}:", student.getName());
        for (Enrollment e : enrollments) {
            log.info("{} => {}", e.getCourse().getTitle(), e.getGrade());
        }
    }

}
