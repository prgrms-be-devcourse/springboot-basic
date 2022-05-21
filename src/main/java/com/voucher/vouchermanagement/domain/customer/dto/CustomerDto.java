package com.voucher.vouchermanagement.domain.customer.dto;

import com.voucher.vouchermanagement.domain.customer.model.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {
    private final UUID id;
    private final String name;
    private final String email;
    private final LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public CustomerDto(UUID id, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public static CustomerDto of(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt(),
                customer.getCreatedAt());
    }

    public UUID getId() {
        return id;
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

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
