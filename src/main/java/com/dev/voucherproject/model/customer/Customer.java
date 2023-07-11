package com.dev.voucherproject.model.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        nameValidate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name) {
        nameValidate(name);
        this.customerId = customerId;
        this.name = name;
    }

    private void nameValidate(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("고객 이름은 빈칸이 될 수 없습니다.");
        }
    }

    public Customer updateEmail(String email) {
        return new Customer(this.customerId, this.name, email, this.createdAt);
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
}
