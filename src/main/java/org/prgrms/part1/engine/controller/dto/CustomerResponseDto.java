package org.prgrms.part1.engine.controller.dto;

import org.prgrms.part1.engine.domain.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerResponseDto {
    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;

    public CustomerResponseDto(Customer entity) {
        this.customerId = entity.getCustomerId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.createdAt = entity.getCreatedAt();
        this.lastLoginAt = entity.getLastLoginAt();
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

    @Override
    public String toString() {
        return "CustomerResponseDto{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
