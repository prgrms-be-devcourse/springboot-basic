package org.programmers.customer.model;

public class Customer {
    private final int customerId;
    private final String name;

    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCustomerId() {
        return customerId;
    }
}
