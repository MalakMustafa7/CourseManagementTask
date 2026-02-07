package org.example.entity;

import lombok.Getter;
import org.example.enumuration.Role;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Enrollment {
    private final Long id;
    private Course course;
    private Student student;
    private double grade;

    private static final AtomicLong counter = new AtomicLong(0);

    public Enrollment(Student student,Course course){
        this.id = counter.incrementAndGet();
        this.student = student;
        this.course = course;
    }

    public void setGrade(double grade) {
        if (grade<0){
            throw new IllegalArgumentException("Grade must be positive");
        }
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return  Objects.equals(course, that.course) && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, student);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", course=" + course.getTitle() +
                ", student=" + student.getName()+
                ", grade=" + grade +
                '}';
    }
}
