package com.programmers.vouchermanagement.customer.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Customer {

    private final UUID id;
    private final String name;
    private CustomerType type;

    public Customer(String name, CustomerType type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
    }

    public Customer(UUID id, String name, CustomerType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public void changeType(CustomerType type) {
        this.type = type;
    }
}
