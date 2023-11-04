package com.programmers.vouchermanagement.customer.domain;

import java.util.UUID;

public class Customer {
    private static final int MAX_NAME_LENGTH = 25;
    private static final String NAME_LENGTH_EXCESSIVE = "Name is too long.";

    private final UUID customerId;
    private final String name;
    private final CustomerType customerType;

    public Customer() {
        customerId = null;
        name = null;
        customerType = null;
    }

    public Customer(UUID customerId, String name) {
        this(customerId, name, CustomerType.NORMAL);
    }

    public Customer(UUID customerId, String name, CustomerType customerType) {
        validateCustomerName(name);
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public boolean isBlack() {
        return customerType == CustomerType.BLACK;
    }

    private void validateCustomerName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(NAME_LENGTH_EXCESSIVE);
        }
    }
}

