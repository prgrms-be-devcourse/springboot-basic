package org.prgrms.springbootbasic.entity;

import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String email;
    private String name;

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void changeName(String newName) {
        name = newName;
    }
}
