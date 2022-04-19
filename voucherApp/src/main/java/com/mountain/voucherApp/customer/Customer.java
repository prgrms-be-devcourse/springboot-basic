package com.mountain.voucherApp.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private UUID voucherId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId,
                    String name,
                    String email,
                    LocalDateTime lastLoginAt,
                    LocalDateTime createdAt) {
        this(customerId, name, email, createdAt);
        this.lastLoginAt = lastLoginAt;
    }

    public Customer(UUID customerId,
                    UUID voucherId,
                    String name,
                    String email,
                    LocalDateTime lastLoginAt,
                    LocalDateTime createdAt) {
        this(customerId, name, email, lastLoginAt, createdAt);
        this.voucherId = voucherId;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
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

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0},{1},{2},{3}{4}",
                voucherId,
                name,
                email,
                lastLoginAt,
                System.lineSeparator()
        );
    }
}
