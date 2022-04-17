package org.prgrms.vouchermanager.customer.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createAt;

    public Customer(UUID customerId, String name, String email) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createAt = LocalDateTime.now();
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createAt, LocalDateTime lastLoginAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createAt = createAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId.equals(customer.customerId) && email.equals(customer.email) && name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, email, createAt, name);
    }
}
