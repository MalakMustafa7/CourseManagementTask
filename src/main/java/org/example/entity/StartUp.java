package org.example.entity;

import org.example.repository.UserRepo;

public class StartUp {
    public static void initialize(UserRepo userRepo) {
        Admin admin = new Admin("Admin", "admin@system.com", "admin");
        userRepo.addUser(admin);
    }
}
