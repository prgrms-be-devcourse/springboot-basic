package com.prgrms.management.customer.domain;

import java.util.UUID;

public class Customer {
    private UUID customerId;
    private CustomerType customerType;

    public Customer(CustomerType customerType) {
        this.customerId = UUID.randomUUID();
        this.customerType = customerType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerType=" + customerType +
                '}';
    }
}