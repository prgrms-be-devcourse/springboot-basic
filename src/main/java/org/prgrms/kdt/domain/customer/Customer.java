package org.prgrms.kdt.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final Name name;
    private final Email email;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastLoginAt;
    private CustomerStatus customerStatus = CustomerStatus.WHITE;

    public Customer(UUID customerId, Name name, Email email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = LocalDateTime.now();
    }

    public Customer(UUID customerId, String customerName, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = new Name(customerName);
        this.email = new Email(email);
        this.lastLoginAt = LocalDateTime.now();
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
