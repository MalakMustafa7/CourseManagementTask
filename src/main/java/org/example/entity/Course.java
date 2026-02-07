package org.example.entity;

import lombok.Getter;
import org.example.enumuration.Role;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Course {
    private final Long id;
    private String title;
    private BigDecimal price;
    private final Instructor instructor;
    private final List<Enrollment> enrollments = new ArrayList<>();

    private static final AtomicLong counter = new AtomicLong(0);

    public Course(String title,BigDecimal price,Instructor instructor){
        this.id = counter.incrementAndGet();
        setTitle(title);
        setPrice(price);
       this.instructor = instructor;
       instructor.addCourses(this);
    }

    public void setTitle(String title) {
        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        if(price==null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than Zero");
        }
        this.price = price;
    }

    public void addEnrollment(Enrollment enrollment){
        if(enrollments.contains(enrollment)){
            throw new IllegalStateException("Enrollment already exists");
        }
        enrollments.add(enrollment);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", instructor=" + instructor.getName() +
                '}';
    }
}
