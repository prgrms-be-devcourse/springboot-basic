package org.prgrms.vouchermanagement.customer;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final int age;

    public Customer(UUID customerId, String name, int age) {
        this.customerId = customerId;
        this.name = name;
        this.age = age;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
