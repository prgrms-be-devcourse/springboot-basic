package org.prgrms.orderapp.model;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.orderapp.CsvParsingUtil.removeSideQuotes;
import static org.prgrms.orderapp.CsvParsingUtil.splitCSV;

public class Customer {
    private final UUID customerId;
    private String name;
    private String address;
    private int age;
    private boolean blacklisted = false;

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

    public static Optional<Customer> createFromString(String s) {
        try {
            String[] args = splitCSV(s);
            UUID customerId = UUID.fromString(args[0]);
            String name = removeSideQuotes(args[1]);
            String address = removeSideQuotes(args[2]);
            int age = Integer.parseInt(args[3]);
            return Optional.of(new Customer(customerId, name, address, age));
        } catch (Exception e) {
            return Optional.empty();
        }
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
