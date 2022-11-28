package com.programmers.customer.domain;

import java.time.LocalDateTime;

public class Customer {
    private final String email;
    private String password;
    private String name;
    private final LocalDateTime createdAt;


    public Customer(String email, String password, String name, LocalDateTime createdAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Customer(String email, String name, LocalDateTime createdAt) {
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
    }

    public void changeName(String newName) {
        this.name = newName;
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
