package com.mountain.voucherApp.model;

import com.mountain.voucherApp.model.vo.Email;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerEntity {
    private final UUID customerId;
    private UUID voucherId;
    private String name;
    private final Email email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public CustomerEntity(UUID customerId,
                          UUID voucherId,
                          String name,
                          Email email,
                          LocalDateTime lastLoginAt,
                          LocalDateTime createdAt) {
        this.customerId = customerId;
        this.voucherId = voucherId;
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

    public Email getEmail() {
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
}
