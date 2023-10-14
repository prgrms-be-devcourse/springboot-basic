package com.prgrms.vouchermanager.domain;

public class Customer {
    int id;
    String name;
    int yearOfBirth;

    public Customer(int id, String name, int yearOfBirth) {
        this.id = id;
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
