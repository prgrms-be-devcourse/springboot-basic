package org.devcourse.voucher.customer.model;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;


    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return customerId + "\t" + name;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
