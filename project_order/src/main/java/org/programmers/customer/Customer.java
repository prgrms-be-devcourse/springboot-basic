package org.programmers.customer;

import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final Gender gender;

    public Customer(UUID customerId, String name, Gender gender) {
        this.customerId = customerId;
        this.name = name;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }

}
