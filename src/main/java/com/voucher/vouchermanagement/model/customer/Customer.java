package com.voucher.vouchermanagement.model.customer;

import java.util.UUID;

public class Customer {

    private final UUID id;
    private String name;

    public Customer(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "customer id = " + id + ", name = '" + name + '\'';
    }
}
