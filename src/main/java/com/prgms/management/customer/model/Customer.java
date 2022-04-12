package com.prgms.management.customer.model;

import java.util.UUID;

public class Customer {
    private final CustomerType type;
    private final UUID customerId;
    private final String name;

    public Customer(String name) {
        this(CustomerType.WHITE, name);
    }

    public Customer(CustomerType type, String name) {
        this(type, UUID.randomUUID(), name);
    }

    public Customer(CustomerType type, UUID customerId, String name) {
        this.type = type;
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer {" + "customerId=" + customerId + ", name='" + name + '\'' + '}';
    }

    public String getStringForCSV() {
        return customerId + "," + name;
    }
}
