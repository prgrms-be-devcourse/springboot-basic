package com.programmers.domain.customer;

import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String customerName;
    private final CustomerType customerType;

    public Customer(UUID customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerType = CustomerType.NORMAL;
    }

    public Customer(String customerName) {
        this.customerId = UUID.randomUUID();
        this.customerName = customerName;
        this.customerType = CustomerType.NORMAL;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    @Override
    public String toString() {
        return "[ Customer Id = " + customerId +
                ", customer name = " + customerName +
                ", type = " + customerType + " ]";
    }
}
