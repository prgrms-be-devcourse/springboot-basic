package com.programmers.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public Customer(UUID customerId, String name, LocalDateTime createdAt) {
        this(customerId, name, createdAt, null);
    }

    public Customer(UUID customerId, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.customerId = customerId;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

//    private void validateCustomerId(UUID customerId) {
//        if (customerId == null) throw new IllegalArgumentException(CUSTOMER_NULL_MESSAGE);
//    }
}
