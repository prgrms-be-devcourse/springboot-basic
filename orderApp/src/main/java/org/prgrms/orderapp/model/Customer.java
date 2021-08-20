package org.prgrms.orderapp.model;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private String address;
    private int age;
    private boolean blacklisted = false;

    public Customer(String name, String address, int age) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public Customer(UUID customerId, String name, String address, int age) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0},\"{1}\",\"{2}\",{3}", customerId, name, address, age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age
                && Objects.equals(customerId, customer.customerId)
                && Objects.equals(name, customer.name)
                && Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, address, age);
    }
}
