package com.programmers.springbasic.domain.customer.entity;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Customer {
    private final UUID customerId;  // Customer 식별값
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }

    public Customer(UUID uuid, String name, String email) {
        this.customerId = uuid;
        this.name = name;
        this.email = email;
    }
}
