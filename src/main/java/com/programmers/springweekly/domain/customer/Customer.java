package com.programmers.springweekly.domain.customer;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class Customer {

    private final UUID customerId;
    private final CustomerType customerType;

    public UUID getCustomerId() {
        return customerId;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }
}
