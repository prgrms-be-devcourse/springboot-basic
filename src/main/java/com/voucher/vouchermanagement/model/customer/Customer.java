package com.voucher.vouchermanagement.model.customer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer implements Serializable {

    private final UUID id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public Customer(UUID id, String name, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "customer id = " + id + ", name = '" + name + '\'';
    }
}
