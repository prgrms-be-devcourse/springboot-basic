package org.programmers.kdtspring.entity.user;

import org.programmers.kdtspring.entity.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Customer {

    private final Long customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;


    public Customer(Long customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(Long customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name Should not be blank");
        }
    }

    public Long getCustomerId() {
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
