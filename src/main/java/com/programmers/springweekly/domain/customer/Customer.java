package com.programmers.springweekly.domain.customer;

import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final CustomerType customerType;

    public Customer(UUID customerId, CustomerType customerType) {
        this.customerId = customerId;
        this.customerType = customerType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }
}
