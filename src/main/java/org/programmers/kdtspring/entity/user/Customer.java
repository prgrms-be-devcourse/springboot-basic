package org.programmers.kdtspring.entity.user;

import org.programmers.kdtspring.entity.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final Email email;
    private final LocalDateTime createdAt;
    private Name name;
    private LocalDateTime lastLoginAt;


    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = new Name(name);
        this.email = new Email(email);
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = new Name(name);
        this.email = new Email(email);
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name Should not be blank");
        }
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getCustomerId(), customer.getCustomerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId());
    }
}
