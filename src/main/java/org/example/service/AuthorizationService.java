package org.example.service;

import org.example.entity.User;
import org.example.enumuration.Permission;
import org.example.repository.UserRepo;

public class AuthorizationService {

    public static  void authorize(User caller, Permission permission){
        if(!caller.hasPermission(permission)){
            throw new SecurityException("permission denied "+permission);
        }

    }
}
