package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepo;

public class AuthService {
    private final UserRepo userRepo;
    public AuthService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User login(String email, String password){
        User user = userRepo.findUserByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        if(!user.getPassword().equals(password)){
            throw new SecurityException("Invalid password");
        }
        return user;
    }
}
