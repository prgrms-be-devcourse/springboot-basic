package com.programmers.assignment.voucher.engine.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private Long customerId;
    private final UUID customerUuid;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerUuid, String name, String email, LocalDateTime createdAt) {
        this.customerUuid = customerUuid;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(Long customerId, UUID customerUuid, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.customerUuid = customerUuid;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public UUID getCustomerUuid() {
        return customerUuid;
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
