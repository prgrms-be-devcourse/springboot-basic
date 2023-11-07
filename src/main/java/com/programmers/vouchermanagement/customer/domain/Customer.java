package com.programmers.vouchermanagement.customer.domain;

import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private final boolean black;

    public Customer(UUID id, String name, boolean black) {
        this.id = id;
        this.name = name;
        this.black = black;
    }

    public Customer(String name, boolean black) {
        if (name == null || name.isBlank() || name.length() > 20)
            throw new IllegalArgumentException("The name length should be between 0 to 20.");
        this.id = UUID.randomUUID();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return black == customer.black && Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, black);
    }
}

