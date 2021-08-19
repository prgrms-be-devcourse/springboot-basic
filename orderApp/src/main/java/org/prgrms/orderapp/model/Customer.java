package org.prgrms.orderapp.model;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private String address;
    private int age;

    public Customer(String name, String address, int age) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public Customer(UUID customerId, String name, String address, int age) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
