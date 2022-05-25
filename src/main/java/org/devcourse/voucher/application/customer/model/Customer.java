package org.devcourse.voucher.application.customer.model;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private Email email;

    public Customer(UUID customerId, String name, Email email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return customerId + "\t" + name;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
