package com.program.commandLine.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegularCustomer implements Customer {
    private final UUID customerId;
    private  final String name;
    private final String email;
    private LocalDateTime lastLoginAt;

    public RegularCustomer(UUID customerId, String name, String email) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public RegularCustomer(UUID customerId, String name, String email, LocalDateTime lastLoginAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
    }


    @Override
    public CustomerType getCustomerType() {
        return CustomerType.REGULAR_CUSTOMER;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void login(){
        lastLoginAt = LocalDateTime.now();
    }
}
