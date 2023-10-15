package com.prgrms.springbasic.domain.customer.entity;

import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String name;

    public Customer(String name) {
        this.customerId = UUID.randomUUID();
        this.name = name;
    }

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
