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
        this.name = name;
        this.email = email;
        this.customerId = customerId;
        this.createAt = createAt;
    }

    public static Customer of(UUID uuid, String name, String email) {
        CustomerValidator.validateEmailFormat(email);
        return new Customer(uuid, name, email, LocalDateTime.now());
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

    public void updateEmail(String email) {
        this.email = email;
    }
}
