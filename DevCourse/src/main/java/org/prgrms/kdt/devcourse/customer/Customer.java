package org.prgrms.kdt.devcourse.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = validateName(name);
        this.email = validateEmail(email);
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = validateName(name);
        this.email = validateEmail(email);
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String validateName(String name) {
        if(name.isBlank())
            throw new IllegalArgumentException("name is should not be blank");
        else
            return name;
    }
    public String validateEmail(String email) {
        if(email.isBlank())
            throw new IllegalArgumentException("email is should not be blank");
        else
            return email;
    }

    public void changeName(String name) {
        this.name = validateName(name);
    }
}
