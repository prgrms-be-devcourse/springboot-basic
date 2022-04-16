package com.prgms.management.customer.model;

import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class Customer {
    private final UUID id;
    private final String name;
    private final CustomerType type;
    private final String email;
    private final Timestamp lastLoginAt;
    private final Timestamp createdAt;

    public Customer(CustomerType type, UUID id, String name) {
        this(id, name, type, "demo", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
    }

    public Customer(String name, CustomerType type, String email) {
        this(UUID.randomUUID(), name, type, email, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
    }

    public Customer(UUID id, String name, CustomerType type, String email, Timestamp lastLoginAt, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public String getStringForCSV() {
        return id + "," + name;
    }
}
