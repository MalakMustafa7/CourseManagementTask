package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.enumuration.Role;


import java.util.ArrayList;
import java.util.List;


@Getter
public class Instructor extends User{
    private final List<Course> courses = new ArrayList<>();

    public Instructor(String name, String email, String password) {
        super(name, email, password, Role.Instructor);
    }

    public void addCourses(Course course){
        if(!courses.contains(course)){
            courses.add(course);
        }
    }

}
