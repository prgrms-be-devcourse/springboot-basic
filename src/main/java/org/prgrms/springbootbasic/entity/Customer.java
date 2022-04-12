package org.prgrms.springbootbasic.entity;

import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final String email;

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }
}
