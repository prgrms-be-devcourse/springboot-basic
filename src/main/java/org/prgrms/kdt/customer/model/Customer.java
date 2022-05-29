package org.prgrms.kdt.customer.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID id;
    private final String name;
    private final LocalDateTime createdAt;

    public Customer(UUID id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
