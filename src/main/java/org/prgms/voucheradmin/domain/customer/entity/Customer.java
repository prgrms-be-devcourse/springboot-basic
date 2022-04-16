package org.prgms.voucheradmin.domain.customer.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
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

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder customerInfoBuilder = new StringBuilder();
        customerInfoBuilder.append(customerId).append("\t")
                .append(name).append("\t")
                .append(email).append("\t");

        return customerInfoBuilder.toString();
    }
}
