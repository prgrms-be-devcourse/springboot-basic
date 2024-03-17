package com.example.demo.domain.customer;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Customer {

    private final UUID id;
    private final String name;
    private final int age;

    @Builder
    public Customer(UUID id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Builder
    public Customer(String name, int age) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
    }
}
