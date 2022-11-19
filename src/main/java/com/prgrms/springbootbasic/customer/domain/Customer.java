package com.prgrms.springbootbasic.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final LocalDateTime createdAt;
    private String name;

    public Customer(UUID id, String name) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.name = name;
    }

    public Customer(UUID id, LocalDateTime createdAt, String name) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
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

    public void update(String name) {
        this.name = name;
    }
}
