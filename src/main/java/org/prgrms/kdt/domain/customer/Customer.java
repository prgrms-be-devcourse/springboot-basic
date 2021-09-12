package org.prgrms.kdt.domain.customer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Customer {
    private final Long customerId;
    private final Name name;
    private final Email email;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private boolean blackStatus = false;

    public Customer(Long customerId, Name name, Email email, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public Long getCustomerId() {
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

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name=" + name +
                ", email=" + email +
                ", createdAt=" + createdAt +
                ", lastLoginAt=" + lastLoginAt +
                ", blackStatus=" + blackStatus +
                '}';
    }
}
