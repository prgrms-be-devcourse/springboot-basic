package com.pgms.part1.domain.customer.entity;

import java.util.UUID;

public class Customer {
    private UUID id;
    private Boolean isBlocked = false;

    public Customer(UUID id, Boolean isBlocked) {
        this.id = id;
        this.isBlocked = isBlocked;
    }

    public UUID getId() {
        return id;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }
}
