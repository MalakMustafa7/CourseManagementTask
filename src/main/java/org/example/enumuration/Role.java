package org.example.enumuration;

import static org.example.enumuration.Permission.*;
import java.util.Set;

public enum Role {
    Student(Set.of(
          ENROLL_STUDENT,
          VIEW_STUDENT_GRADES,
          VIEW_STUDENT_ENROLLMENTS

    )),
    Instructor(Set.of(
            ASSIGN_GRADE,
            VIEW_INSTRUCTOR_COURSES
    )),
    Admin(Set.of(
            CREATE_USER,
            CREATE_COURSE,
            VIEW_ALL_COURSES,
            DELETE_COURSE,
            DELETE_USER,
            VIEW_ALL_STUDENTS,
            VIEW_ALL_INSTRUCTORS
    ));


    private final Set<Permission> permissions ;

     Role(Set<Permission> permissions){
        this.permissions = permissions;
    }

    public boolean hasPermission(Permission permission){
         return permissions.contains(permission);
    }

}
