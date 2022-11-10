package com.programmers.kwonjoosung.springbootbasicjoosung.model.customer;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;

    public Customer(UUID customerId, String name){
        this.customerId = customerId;
        this.name = name;
    }

    public Customer(String name){
        this(UUID.randomUUID(),name);
    }

    @Override
    public String toString() {
        return "Customer { " +
                "customerId = " + customerId +
                ", name = " + name + " }";
    }
}
