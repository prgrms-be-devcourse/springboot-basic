package com.prgrms.kdt.springbootbasic.entity;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final String email;

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
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
}
