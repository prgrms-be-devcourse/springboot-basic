package com.prgrms.springbasic.domain.customer.entity;

import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String name;
    private String email;

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
