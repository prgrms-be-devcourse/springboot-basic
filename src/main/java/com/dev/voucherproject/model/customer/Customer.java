package com.dev.voucherproject.model.customer;

import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String name;

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
