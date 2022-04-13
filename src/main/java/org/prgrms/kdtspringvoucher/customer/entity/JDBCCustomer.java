package org.prgrms.kdtspringvoucher.customer.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class JDBCCustomer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    private void validate(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank.");
        }
    }

    public JDBCCustomer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }
    public JDBCCustomer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public void changeName(String name) {
        validate(name);
        this.name = name;
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
}
