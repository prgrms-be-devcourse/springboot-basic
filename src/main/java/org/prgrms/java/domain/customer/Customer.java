package org.prgrms.java.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private String email;
    private final LocalDateTime createdAt;
    private boolean isBlocked;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this(customerId, name, email, createdAt, false);
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt, boolean isBlocked) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.isBlocked = isBlocked;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", customerId, name, email, createdAt, isBlocked);
    }
}
