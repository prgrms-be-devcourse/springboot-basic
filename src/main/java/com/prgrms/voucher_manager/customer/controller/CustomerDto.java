package com.prgrms.voucher_manager.customer.controller;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.SimpleCustomer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {

    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;

    public CustomerDto() {}

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public CustomerDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    static CustomerDto of(Customer customer) {
        return customer.toCustomerDto();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
