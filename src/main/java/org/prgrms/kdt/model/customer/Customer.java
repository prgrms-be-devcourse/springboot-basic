package org.prgrms.kdt.model.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String email;
    private final LocalDateTime createdAt;
    private final boolean isBlacklist;
    private String name;
    private LocalDateTime lastLoginAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt, boolean isBlacklist) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.isBlacklist = isBlacklist;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt, boolean isBlacklist) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.isBlacklist = isBlacklist;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                '}';
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (name.isBlank()) {
            throw new IllegalArgumentException("공백은 이름이 될 수 없습니다.");
        }

        this.name = name;
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

    public boolean isBlacklist() {
        return isBlacklist;
    }
}
