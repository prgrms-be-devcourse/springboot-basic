package com.programmers.vouchermanagement.domain.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Customer {
    private UUID id;
    private String name;
    private boolean blacklisted;

    public Customer(UUID id, String name, boolean blacklisted) {
        this.id = id;
        this.name = name;
        this.blacklisted = blacklisted;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blacklisted=" + blacklisted +
                '}';
    }

    public static Customer parseCsvLine(String line) {
        String[] parts = line.split(",");
        return new Customer(UUID.fromString(parts[0]), parts[1], parts[2].equals("1"));
    }
}
