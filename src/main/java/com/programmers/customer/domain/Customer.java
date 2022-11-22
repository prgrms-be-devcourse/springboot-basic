package com.programmers.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;
    private final String password;
    private String name;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String email, String password, String name, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public UUID getCustomerId() {
        return customerId;
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
