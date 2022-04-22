package org.prgms.kdtspringvoucher.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String name;
    private final String email;
    private CustomerType customerType;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, CustomerType customerType, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.customerType = customerType;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, CustomerType customerType, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.customerType = customerType;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
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

    public CustomerType getCustomerType() {
        return customerType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void changeCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return "customerId=" + customerId +
                ", name='" + name +
                ", email='" + email +
                ", customerType=" + customerType +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt;
    }
}
