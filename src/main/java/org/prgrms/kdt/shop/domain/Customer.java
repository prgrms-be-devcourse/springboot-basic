package org.prgrms.kdt.shop.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final LocalDateTime createAt;
    private final String email;
    private String name;
    private LocalDateTime lastLoginAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createAt) {
        validateName(name);
        this.customerId = customerId;
        this.createAt = createAt;
        this.email = email;
        this.name = name;
        this.lastLoginAt = lastLoginAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createAt) {
        validateName(name);
        this.customerId = customerId;
        this.createAt = createAt;
        this.email = email;
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public void login( ) {
        this.lastLoginAt = LocalDateTime.now();
    }

    public UUID getCustomerId( ) {
        return customerId;
    }

    public LocalDateTime getCreateAt( ) {
        return createAt;
    }

    public String getEmail( ) {
        return email;
    }

    public String getName( ) {
        return name;
    }

    public LocalDateTime getLastLoginAt( ) {
        return lastLoginAt;
    }
}
