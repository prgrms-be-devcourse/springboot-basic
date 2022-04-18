package org.prgms.management.customer.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;

    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
