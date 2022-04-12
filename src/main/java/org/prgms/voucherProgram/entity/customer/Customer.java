package org.prgms.voucherProgram.entity.customer;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "userId=" + customerId + ", name=" + name;
    }
}
