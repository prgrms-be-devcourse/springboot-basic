package org.prgrms.vouchermanagement.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    private Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static Customer createNormalCustomer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        return new Customer(customerId, name, email, createdAt);
    }

    public static Customer createBlackCustomer(UUID customerId, String name, String email) {
        return new Customer(customerId, name, email, LocalDateTime.now());
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

    public String toString() {
        return "ID : " + this.customerId + " NAME : " + this.name + " EMAIL : " + this.email;
    }
}
