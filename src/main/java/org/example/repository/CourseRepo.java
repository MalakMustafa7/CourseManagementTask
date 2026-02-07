package org.example.repository;

import org.example.entity.Course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CourseRepo {
    private Map<Long, Course> courses = new HashMap<>();

    public void addCourse(Course course){
        if(courseExists(course.getId())){
            throw new IllegalArgumentException("course already exist");
        }
        courses.put(course.getId(), course);
    }

    public boolean courseExists(Long courseId){
        return courses.containsKey(courseId);
    }

    public Optional<Course> findCourseById(Long courseId){
        return Optional.ofNullable(courses.get(courseId));
    }

    public void deleteCourseById(Long courseId){
        courses.remove(courseId);
    }

    public List<Course> getAllCourses(){
        return courses.values().stream().toList();
    }

    public List<Course> getCoursesByInstructor(Long instructorId){
        return courses.values().stream()
                .filter(course ->
                        course.getInstructor() != null &&
                                instructorId.equals(course.getInstructor().getId())
                )
                .toList();
    }



}
