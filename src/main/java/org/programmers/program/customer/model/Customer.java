package org.programmers.program.customer.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String email;
    private final String name;
    private final boolean isBlackConsumer;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

    public Customer(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
        this.isBlackConsumer = false;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean getIsBlackConsumer() {
        return isBlackConsumer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
}
