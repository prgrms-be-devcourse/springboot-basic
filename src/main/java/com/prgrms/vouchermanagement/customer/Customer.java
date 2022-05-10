package com.prgrms.vouchermanagement.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;

public class Customer {

    private Long customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    private Customer(Long customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public static Customer of(Long customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        return new Customer(customerId, name, email, lastLoginAt, createdAt);
    }

    public static Customer of(String name, String email) {
        return new Customer(null, name, email, null, LocalDateTime.now());
    }

    public Long getCustomerId() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return MessageFormat.format("customerId={0}, name={1}, email={2}", customerId, name, email);
    }
}
