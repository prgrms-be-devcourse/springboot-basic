package org.programmers.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final Gender gender;
    private final String email;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public Customer(UUID customerId, String name, Gender gender, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, Gender gender, String email, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.customerId = customerId;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", createdAt=" + createdAt +
                ", lastLoginAt=" + lastLoginAt +
                '}';
    }

}
