package com.voucher.vouchermanagement.domain.customer.model;

import static com.voucher.vouchermanagement.utils.datetime.LocalDateTimeUtils.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Customer {

    private final UUID id;
    private final String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID id, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.lastLoginAt = toMicrosLocalDateTime(lastLoginAt);
        this.createdAt = toMicrosLocalDateTime(createdAt);
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void login() {
        this.lastLoginAt = toMicrosLocalDateTime(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "customer id = " + id + ", name = '" + name + '\'';
    }
}
