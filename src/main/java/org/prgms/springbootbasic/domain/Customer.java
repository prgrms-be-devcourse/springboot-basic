package org.prgms.springbootbasic.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private boolean isBlacked;

    public Customer(UUID id, String name, String email, LocalDateTime createdAt, LocalDateTime lastLoginAt, boolean isBlacked) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
        this.isBlacked = isBlacked;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", lastLoginAt=" + lastLoginAt +
                ", isBlacked=" + isBlacked +
                '}';
    }
}
