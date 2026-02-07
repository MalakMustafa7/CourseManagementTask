package org.example.entity;


import org.example.enumuration.Role;

public class Admin extends User{
    public Admin(String name, String email, String password) {
        super(name, email, password, Role.Admin);
    }

}
