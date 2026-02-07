package org.example.repository;

import org.example.entity.Instructor;
import org.example.entity.Student;
import org.example.entity.User;
import org.example.enumuration.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepo {
    private Map<Long, User> users = new HashMap<>();
    private Map<String, User> usersByEmail = new HashMap<>();

    public void addUser(User user){

        users.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);
    }

    public boolean isEmailExist(String email){
        return usersByEmail.containsKey(email);
    }

    public Optional<User> findUserByEmail(String email){
        return Optional.ofNullable(usersByEmail.get(email));
    }

    public Optional<User> findUserById(Long userId){
        return Optional.ofNullable(users.get(userId));
    }
    public Optional<Instructor> findInstructorById(Long id) {
        User user = users.get(id);

        return  (user instanceof Instructor instructor)
           ? Optional.of(instructor)
           : Optional.empty();
    }
    public Optional<Student> findStudentById(Long id) {
        User user = users.get(id);

        return  (user instanceof Student student)
                ? Optional.of(student)
                : Optional.empty();
    }

    public void deleteUserById(Long userId){
        users.remove(userId);
    }

    public List<Student> getAllStudents(){
        return users.values().stream()
                .filter(user-> user instanceof Student)
                .map(user -> (Student) user)
                .toList();
    }
    public List<Instructor> getAllInstructor(){
        return users.values().stream()
                .filter(user->user instanceof Instructor)
                .map(user -> (Instructor) user)
                .toList();
    }
}
