package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.enumuration.Role;


import java.util.ArrayList;
import java.util.List;



@Getter
public class Student extends User{

    private final List<Enrollment> enrollments = new ArrayList<>();
    public Student(String name, String email, String password) {
        super(name, email, password, Role.Student);
    }



    public void addEnrollment(Enrollment enrollment){
        if(enrollments.contains(enrollment)){
            throw new IllegalStateException("Already enrolled in this course");
        }
        enrollments.add(enrollment);
    }

}
