package org.weekly.weekly.customer.domain;

import org.weekly.weekly.customer.exception.CustomerValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    UUID customerId;
    String name;
    String email;
    LocalDateTime createAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.customerId = customerId;
    }

    private Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public static Customer of(UUID uuid, String name, String email) {
        CustomerValidator.validateEmailFormat(email);
        return new Customer(uuid, name, email);
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getEmail() {
        return email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
