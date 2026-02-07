package org.example.entity;

import lombok.Getter;
import org.example.enumuration.Permission;
import org.example.enumuration.Role;

import java.util.concurrent.atomic.AtomicLong;

@Getter
public abstract class User {
    private final Long id;
    protected String name;
    protected String email;
    private String password;
    private Role role;


    private static final AtomicLong counter = new AtomicLong(0);
    public User(String name,String email,String password , Role role){
        this.id = counter.incrementAndGet();
        setName(name);
        setEmail(email);
        setPassword(password);
        this.role = role;

    }

    public void setName(String name) {
        validateData(name,"Name");
        this.name = name;
    }

    public void setEmail(String email) {
        validateData(email,"Email");
        this.email = email;
    }

    public void setPassword(String password) {
        validateData(password,"Password");
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    private void validateData(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required and cannot be blank");
        }
    }

    public boolean hasPermission(Permission permission){
        return role.hasPermission(permission);
    }
}
