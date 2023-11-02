package com.programmers.vouchermanagement.customer.domain;

import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private final boolean black;

    public Customer(UUID id, String name) {
        this(id, name, false);
    }

    public Customer(UUID id, String name, boolean black) {
        this.id = id;
        this.name = name;
        this.black = black;
    }

    public UUID getId() {
        return id;
    }

    public boolean isBlack() {
        return black;
    }

    public String getName() {
        return name;
    }
}

