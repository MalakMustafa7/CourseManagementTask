package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Instructor;
import org.example.entity.Student;
import org.example.entity.User;
import org.example.enumuration.Permission;
import org.example.enumuration.Role;
import org.example.repository.UserRepo;

import java.util.List;

@Slf4j
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public void createUser(User admin, String name, String email, String password, Role role){
       AuthorizationService.authorize(admin, Permission.CREATE_USER);
        if(userRepo.findUserByEmail(email).isPresent()){
           throw new IllegalArgumentException("user already exist");
       }
        User user = switch (role) {
            case Role.Student    -> new Student(name, email, password);
            case Role.Instructor -> new Instructor(name, email, password);
            default -> throw new IllegalArgumentException("un supported");
        };

       userRepo.addUser(user);
       log.info("user with email {} created successfully",user.getEmail());
    }



    public void deleteUser(User admin,Long userId){
        AuthorizationService.authorize(admin, Permission.DELETE_USER);
        User user = userRepo.findUserById(userId)
                .orElseThrow(()-> new IllegalArgumentException("user is not exist"));
        userRepo.deleteUserById(userId);
        log.info("user deleted ");
    }


    public List<Student> getAllStudents(User admin){
        AuthorizationService.authorize(admin, Permission.VIEW_ALL_STUDENTS);
         return userRepo.getAllStudents();
    }

    public List<Instructor> getAllInstructor(User admin){
        AuthorizationService.authorize(admin, Permission.VIEW_ALL_INSTRUCTORS);
        return userRepo.getAllInstructor();
    }

}
