package org.programmers.springboot.basic.domain.customer.entity;

import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final CustomerType customerType;

    public Customer(UUID customerId, String name, CustomerType customerType) {
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public String getName() {
        return this.name;
    }

    public CustomerType getCustomerType() {
        return this.customerType;
    }
}
