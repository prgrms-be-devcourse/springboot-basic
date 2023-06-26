package com.programmers.springweekly.domain.customer;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

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
