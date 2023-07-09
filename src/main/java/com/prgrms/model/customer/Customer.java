package com.prgrms.model.customer;

import com.prgrms.view.message.ErrorMessage;

import java.time.LocalDateTime;

public class Customer {

    private final int customerId;
    private final String email;
    private final LocalDateTime createdAt;
    private String name;
    private LocalDateTime lastLoginAt;

    public Customer(int customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            throw new RuntimeException(ErrorMessage.NULL_ARGUMENT.getMessage());
        }
    }

    public Customer(int customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
