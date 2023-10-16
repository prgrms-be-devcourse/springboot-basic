package org.prgrms.kdtspringdemo.customer.domain;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final boolean isBlack;

    public Customer(UUID customerId, String name, boolean isBlack) {
        this.customerId = customerId;
        this.name = name;
        this.isBlack = isBlack;
    }
}
