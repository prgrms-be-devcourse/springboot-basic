package me.programmers.springboot.basic.springbootbasic.customer.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private CustomerInfo customerInfo;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, CustomerInfo customerInfo, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.customerInfo = customerInfo;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void changeName(String name) {
        customerInfo.changeName(name);
    }
}

