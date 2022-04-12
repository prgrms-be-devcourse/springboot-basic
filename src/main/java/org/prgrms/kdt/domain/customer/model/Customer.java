package org.prgrms.kdt.domain.customer.model;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                '}';
    }
}
