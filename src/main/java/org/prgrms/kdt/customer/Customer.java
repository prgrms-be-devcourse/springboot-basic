package org.prgrms.kdt.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private String name;

    public Customer(final UUID customerId, final String name, final String email, final LocalDateTime createdAt) {
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;
        this.name = name;
    }

    public Customer(final UUID customerId, final String name, final String email, final LocalDateTime lastLoginAt, final LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return name;
    }

    private void validateName(final String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public void changeName(final String name) {
        validateName(name);
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
