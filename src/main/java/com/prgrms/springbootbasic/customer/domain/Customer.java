package com.prgrms.springbootbasic.customer.domain;

import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;

    public Customer(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
