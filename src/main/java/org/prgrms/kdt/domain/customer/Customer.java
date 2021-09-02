package org.prgrms.kdt.domain.customer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final Name name;
    private final Email email;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private boolean blackStatus = false;

    public Customer(UUID customerId, Name name, Email email, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
}
