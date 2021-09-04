package com.prgms.kdtspringorder.domain.model.customer;

import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private final int age;

    public Customer(UUID id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
