package com.prgrms.vouchermanager.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Customer {
    private final UUID id;
    private final String name;
    private final int yearOfBirth;

    public Customer(UUID id, String name, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public Customer(String name, int yearOfBirth) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public String toString() {
        return """
                Id : %d
                Name : %s
                YearOfBirth: %d
                """.formatted(id, name, yearOfBirth);
    }
}
