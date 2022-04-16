package org.prgrms.vouchermanager.customer.domain;

import java.time.LocalDateTime;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId.equals(customer.customerId) && email.equals(customer.email) && createAt.equals(customer.createAt) && name.equals(customer.name) && Objects.equals(lastLoginAt, customer.lastLoginAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, email, createAt, name);
    }
}
