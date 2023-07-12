package com.example.demo.domain.customer;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Customer {

    private final UUID customerId;
    private final String name;
    private final int age;

    public Customer(UUID customerId, String name, int age) {
        this.customerId = customerId;
        this.name = name;
        this.age = age;
    }

    public Customer(String name, int age) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.age = age;
    }
}
