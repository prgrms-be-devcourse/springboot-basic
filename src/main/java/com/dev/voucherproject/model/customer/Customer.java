package com.dev.voucherproject.model.customer;

import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String customerName;

    public Customer(UUID customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
}
