package org.programmers.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email) {
        this(customerId, name, email, LocalDateTime.now(), LocalDateTime.now());
    }

    public void validateName(String name) {
        if (name.isEmpty()) {
            throw new RuntimeException("이름이 비어 있음");
        }
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
}
