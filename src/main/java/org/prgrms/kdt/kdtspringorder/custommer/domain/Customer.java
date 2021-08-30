package org.prgrms.kdt.kdtspringorder.custommer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
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

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "customerId=" + customerId +
            ", name='" + name + '\'' +
            '}';
    }

}
