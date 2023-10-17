package com.programmers.vouchermanagement.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID id;
    private final String name;

    private final LocalDateTime createdAt;

    public Customer(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public Customer(UUID id, String name, String createdAt) {

        this.id = UUID.randomUUID();
        this.name = name;
        this.createdAt = LocalDateTime.parse(createdAt);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }

}
