package com.programmers.kwonjoosung.springbootbasicjoosung.model.customer;

import java.util.Objects;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public Customer(String name) {
        this(UUID.randomUUID(), name);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer { " + "customerId = " + customerId + ", name = " + name + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return customerId.equals(customer.customerId) && name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name);
    }
}
