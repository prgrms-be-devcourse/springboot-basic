package org.prgrms.devcourse.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private static final int MIN_NAME_LENGTH = 4;
    private static final int MAX_NAME_LENGTH = 20;

    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customer_id, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customer_id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customer_id, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customer_id;
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

    public UUID getCustomerId() {
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

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }

        if (name.length() < MIN_NAME_LENGTH) {
            throw new RuntimeException("Name should be longer than 4 letters");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            throw new RuntimeException("Name should be shorter than 20 letters");
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
