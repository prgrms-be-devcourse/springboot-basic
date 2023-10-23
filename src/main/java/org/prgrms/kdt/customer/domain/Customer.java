package org.prgrms.kdt.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime joinedAt;
    private boolean isBlacked;

    public Customer(UUID id, String name, String email, LocalDateTime joinedAt, boolean isBlacked) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.joinedAt = joinedAt;
        this.isBlacked = isBlacked;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + joinedAt +
                ", isBlacked=" + isBlacked +
                '}';
    }
}
