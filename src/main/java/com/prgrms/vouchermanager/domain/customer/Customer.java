package com.prgrms.vouchermanager.domain.customer;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Customer {
    private final UUID id;
    private final String name;
    private final int yearOfBirth;
    private final boolean isBlacklist;
    public Customer(UUID id, String name, int yearOfBirth, boolean isBlacklist) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.isBlacklist = isBlacklist;
    }

    public Customer(String name, int yearOfBirth) {
        this(UUID.randomUUID(), name, yearOfBirth, false);
    }

    @Override
    public String toString() {
        return """
                Id : %s
                Name : %s
                YearOfBirth : %d
                Blacklist : %s
                """.formatted(id.toString(), name, yearOfBirth, String.valueOf(isBlacklist));
    }
}
