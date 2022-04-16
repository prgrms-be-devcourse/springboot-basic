package org.prgrms.vouchermanager.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;
    private final LocalDateTime createAt;
    private String name;
    private LocalDateTime lastLoginAt;

    public Customer(UUID customerId, String name, String email) {
        validate(name);
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createAt = LocalDateTime.now();
    }

    public void login() {
        lastLoginAt = LocalDateTime.now();
    }

    public void changeName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name.isBlank()) throw new IllegalArgumentException("Name should not be blank.");
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

}
