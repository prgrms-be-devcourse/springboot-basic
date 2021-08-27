package org.prgrms.kdt.model;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final CustomerType type;

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.type = CustomerType.NEW;
    }

    public Customer(UUID customerId, String name, CustomerType type) {
        this.customerId = customerId;
        this.name = name;
        this.type = type;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public CustomerType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "customerId=" + customerId +
            ", name='" + name + '\'' +
            ", type=" + type +
            '}';
    }
}
