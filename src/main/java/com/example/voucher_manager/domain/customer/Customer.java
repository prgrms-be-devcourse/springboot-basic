package com.example.voucher_manager.domain.customer;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "[customerId : " + customerId + ", name : " + name + ", email : " + email +"]";
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

    public void changeName(String name) {
        this.name = name;
    }
}
