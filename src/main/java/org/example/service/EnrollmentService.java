package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.*;
import org.example.enumuration.Permission;
import org.example.repository.CourseRepo;
import org.example.repository.EnrollmentRepo;
import org.example.repository.UserRepo;

import java.util.List;

@Slf4j
public class EnrollmentService {
    private final EnrollmentRepo enrollmentRepo;
    private final UserRepo userRepo;
    private final CourseRepo courseRepo;
    public EnrollmentService(EnrollmentRepo enrollmentRepo , UserRepo userRepo, CourseRepo courseRepo){
        this.enrollmentRepo = enrollmentRepo;
        this.userRepo = userRepo;
        this.courseRepo = courseRepo;
    }

    public void enrollStudent(User caller,Long studentId,Long courseId){
        AuthorizationService.authorize(caller,Permission.ENROLL_STUDENT);
        if(!caller.getId().equals(studentId)){
            throw new SecurityException("you can only enroll yourself");
        }
        Student student = userRepo.findStudentById(studentId)
                .orElseThrow(()->new IllegalArgumentException("This Student is not found"));

        Course course = courseRepo.findCourseById(courseId)
                .orElseThrow(()->new IllegalArgumentException("This course is not found"));

        if (enrollmentRepo.findEnrollment(studentId, courseId).isPresent()) {
            throw new IllegalStateException("Student already enrolled in this course");
        }
        Enrollment enrollment= new Enrollment(student,course);

        student.addEnrollment(enrollment);
        course.addEnrollment(enrollment);
        enrollmentRepo.addEnrollment(enrollment);

       log.info("Student has enrolled in {} successfully",course.getTitle());
    }

    public List<Enrollment> getEnrollmentsForStudent(User student){
        AuthorizationService.authorize(student, Permission.VIEW_STUDENT_ENROLLMENTS);
        return enrollmentRepo.getEnrollmentsForStudent(student.getId());
    }
}
