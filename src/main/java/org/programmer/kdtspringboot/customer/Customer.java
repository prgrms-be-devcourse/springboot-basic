package org.programmer.kdtspringboot.customer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createAt = createAt;
    }

    public Customer(String name, String email) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        lastLoginAt = LocalDateTime.now();
        createAt = LocalDateTime.now();
    }
}
