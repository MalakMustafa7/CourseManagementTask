package org.example.repository;

import org.example.entity.Enrollment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnrollmentRepo {
    private Map<Long, Enrollment> enrollments = new HashMap<>();

    public void addEnrollment(Enrollment enrollment){
        enrollments.put(enrollment.getId(), enrollment);
    }



    public Optional<Enrollment> findEnrollment(Long studentId, Long courseId){
       return enrollments.values().stream()
                .filter(enrollment -> enrollment.getStudent().getId().equals(studentId)
                           && enrollment.getCourse().getId().equals(courseId))
               .findFirst();
    }

    public List<Enrollment>getEnrollmentsForStudent(Long studentId){
        return enrollments.values().stream()
                .filter(enrollment ->
                        enrollment.getStudent() != null &&
                                studentId.equals(enrollment.getStudent().getId())
                ).toList();
    }

}
