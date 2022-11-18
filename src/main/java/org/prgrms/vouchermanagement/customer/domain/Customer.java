package org.prgrms.vouchermanagement.customer.domain;

import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final String email;

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

    public String toString() {
        return "ID : " + this.customerId + " NAME : " + this.name + " EMAIL : " + this.email;
    }
}
