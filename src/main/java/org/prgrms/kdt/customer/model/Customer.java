package org.prgrms.kdt.customer.model;

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

    @Override
    public String toString() {
        return "Customer{" +
            "customerId=" + customerId +
            ", name='" + name + '\'' +
            ", createdAt=" + createdAt +
            '}';
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
