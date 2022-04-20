package org.prgrms.vouchermanager.customer.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;
    private final LocalDateTime createAt;
    private String name;
    private LocalDateTime lastLoginAt;

    public Customer(UUID customerId, String name, String email) {
        validateName(name);
        validateEmail(email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    private void validateEmail(String email) {
        if (email.isBlank()) throw new IllegalArgumentException("email should not be blank.");
        if (!email.contains("@")) throw new IllegalArgumentException("email should contain '@'.");
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createAt, LocalDateTime lastLoginAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createAt = createAt;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
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
        return customerId.equals(customer.customerId) && name.equals(customer.name) && email.equals(customer.email) && Objects.equals(lastLoginAt, customer.lastLoginAt) && createAt.equals(customer.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, email, createAt, name);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", createAt=" + createAt +
                '}';
    }
}
