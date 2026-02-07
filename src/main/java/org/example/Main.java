package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.*;
import org.example.enumuration.Role;
import org.example.repository.CourseRepo;
import org.example.repository.EnrollmentRepo;
import org.example.repository.UserRepo;
import org.example.service.*;

import java.math.BigDecimal;
import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) {

        UserRepo userRepo = new UserRepo();
        StartUp.initialize(userRepo);
        CourseRepo courseRepo = new CourseRepo();
        EnrollmentRepo enrollmentRepo = new EnrollmentRepo();


        AuthService authService = new AuthService(userRepo);
        UserService userService = new UserService(userRepo);
        CourseService courseService = new CourseService(courseRepo, userRepo);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepo, userRepo, courseRepo);
        GradeService gradeService = new GradeService(courseRepo, userRepo, enrollmentRepo);


        User admin = authService.login("admin@system.com", "admin");
        log.info("Admin logged in: {}", admin.getName());


        userService.createUser(admin, "Sara", "sara@teach.com", "123",Role.Instructor);
        userService.createUser(admin, "Fatma", "fatma@teach.com", "123",Role.Instructor);
        userService.createUser(admin, "Ali", "ali@student.com", "123",Role.Student);



        courseService.createCourse(admin, "Java Basics", BigDecimal.valueOf(500),2L);
        courseService.createCourse(admin, "Java OOP", BigDecimal.valueOf(700),2L);
        courseService.createCourse(admin, "Java collectins", BigDecimal.valueOf(600),3L);

        log.info("\n------------All Courses-----------");
        courseService.getAllCourses(admin).forEach(System.out::println);
        log.info("\n------------All Instructor-----------");
        userService.getAllInstructor(admin).forEach(System.out::println);
        log.info("\n------------All Students-----------");
        userService.getAllStudents(admin).forEach(System.out::println);



        Student student = userRepo.findStudentById(4L).get();
        enrollmentService.enrollStudent(student,4L,1L);
        enrollmentService.enrollStudent(student,4L,2L);
        enrollmentService.enrollStudent(student,4L,3L);


        Instructor instructor1 = userRepo.findInstructorById(2L).get();
        Instructor instructor2 = userRepo.findInstructorById(3L).get();

        gradeService.assignGrade(instructor1, student.getId(),1L, 95);
        gradeService.assignGrade(instructor1, student.getId(),2L, 90);
        gradeService.assignGrade(instructor2, student.getId(),3L, 80);


         log.info("\n--------------Student grades-----------");
        gradeService.showStudentGrades(student);

        log.info("\n--------------Instructor courses-----------");
        courseService.getCourseByInstructor(instructor1).forEach(System.out::println);


    }
}


