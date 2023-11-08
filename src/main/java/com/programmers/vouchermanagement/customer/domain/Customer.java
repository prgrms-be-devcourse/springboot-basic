package com.programmers.vouchermanagement.customer.domain;

import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private final boolean isBlack;

    public Customer(UUID id, String name, boolean isBlack) {
        validateName(name);
        this.id = id;
        this.name = name;
        this.isBlack = isBlack;
    }

    public Customer(String name, boolean isBlack) {
        this(UUID.randomUUID(), name, isBlack);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || name.length() > 20)
            throw new IllegalArgumentException("The name length should be between 0 to 20.");
    }

    public UUID getId() {
        return id;
    }

    public boolean getIsBlack() {
        return isBlack;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return isBlack == customer.isBlack && Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isBlack);
    }
}

