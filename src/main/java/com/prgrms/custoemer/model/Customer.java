package com.prgrms.custoemer.model;

import java.time.LocalDateTime;

public class Customer {

    private final String customerId;
    private final String email;
    private final LocalDateTime createdAt;
    private final Name name;
    private LocalDateTime lastLoginAt;

    public Customer(String customerId, Name name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(String customerId, Name name, String email, LocalDateTime lastLoginAt,
            LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void login(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Name getName() {
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
