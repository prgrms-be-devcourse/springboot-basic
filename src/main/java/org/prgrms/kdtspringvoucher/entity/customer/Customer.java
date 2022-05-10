package org.prgrms.kdtspringvoucher.entity.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;
    private CustomerType customerType = CustomerType.WHITE;

    private void validate(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank.");
        }
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt, CustomerType customerType) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.customerType = customerType;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt, CustomerType customerType) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.customerType = customerType;
    }

    public void changeName(String name) {
        validate(name);
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

    public int getCustomerType() {
        return customerType.ordinal() + 1;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                ", type=" + customerType.toString() +
                '}';
    }
}
