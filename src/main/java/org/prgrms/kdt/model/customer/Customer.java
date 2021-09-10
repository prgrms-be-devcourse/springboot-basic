package org.prgrms.kdt.model.customer;

import java.util.UUID;

import java.time.LocalDateTime;

public class Customer {

    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;
    private CustomerType customerType;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.customerType = CustomerType.NEW;

    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt,
        LocalDateTime createdAt, CustomerType customerType) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.customerType = customerType;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void changeCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public void login(LocalDateTime lastLoginAt) {
        this.lastLoginAt = LocalDateTime.now();
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

    public CustomerType getCustomerType() {
        return customerType;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "customerId=" + customerId +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", lastLoginAt=" + lastLoginAt +
            ", createdAt=" + createdAt +
            ", customerType=" + customerType +
            '}';
    }
}