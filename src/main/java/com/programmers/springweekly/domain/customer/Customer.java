package com.programmers.springweekly.domain.customer;

import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String customerType;

    public Customer(UUID customerId, String customerType) {
        this.customerId = customerId;
        this.customerType = customerType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getCustomerType() {
        return customerType;
    }
}
