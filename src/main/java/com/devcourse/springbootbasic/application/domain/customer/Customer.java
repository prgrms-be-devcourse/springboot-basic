package com.devcourse.springbootbasic.application.domain.customer;

import java.text.MessageFormat;

public class Customer {
    private final int customerId;
    private final String name;

    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Customer(id: {0}, name : {1})", customerId, name);
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
