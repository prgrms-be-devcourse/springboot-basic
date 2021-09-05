package org.programmers.customer.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    @Override
    public String toString() {
        return "Customer ID : " + customerId + " / " + "Name : " + name + " / " + "Email : " + email;
    }
}
