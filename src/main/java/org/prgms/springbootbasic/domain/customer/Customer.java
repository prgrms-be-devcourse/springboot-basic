package org.prgms.springbootbasic.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private boolean isBlacked;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt, LocalDateTime lastLoginAt, boolean isBlacked) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
        this.isBlacked = isBlacked;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", lastLoginAt=" + lastLoginAt +
                ", isBlacked=" + isBlacked +
                '}';
    }
}
