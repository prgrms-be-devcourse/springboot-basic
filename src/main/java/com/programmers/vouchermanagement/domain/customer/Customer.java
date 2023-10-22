package com.programmers.vouchermanagement.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String customerName;
    private final LocalDateTime createdAt;
    private boolean isBanned;

    public Customer(UUID customerId, String customerName, LocalDateTime createdAt, boolean isBanned) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.isBanned = isBanned;
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "#######################" + System.lineSeparator() +
                "Customer Id:   " + customerId + System.lineSeparator() +
                "Customer Name: " + customerName + System.lineSeparator() +
                "Created At:    " + createdAt + System.lineSeparator() +
                "isBanned:      " + isBanned + System.lineSeparator();
    }
}
