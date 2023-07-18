package com.example.voucher.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.voucher.constant.CustomerType;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final String email;
    private final CustomerType customerType;
    private final LocalDateTime createdAt;

    public Customer(String name, String email, CustomerType customerType) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.customerType = customerType;
        this.createdAt = LocalDateTime.now();
    }

    public Customer(UUID customerId, String name, String email, CustomerType customerType) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.customerType = customerType;
        this.createdAt = LocalDateTime.now();
    }

    public Customer(UUID customerId, String name, String email, CustomerType customerType, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.customerType = customerType;
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

}
