package com.prgrms.management.customer.domain;

import com.prgrms.management.customer.domain.CustomerType;

import java.util.UUID;

public class Customer {
    private UUID customerId;
    private CustomerType customerType;

    public Customer(UUID customerId, CustomerType customerType) {
        this.customerId = customerId;
        this.customerType = customerType;
    }

}