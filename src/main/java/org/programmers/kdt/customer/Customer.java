package org.programmers.kdt.customer;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email; // 변경가능해야할 것 같은데 강의 내용 때문에...
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public static class Builder {
        private final UUID customerId;
        private final String email;
        private final LocalDateTime createdAt;

        private String name = "";
        private LocalDateTime lastLoginAt = LocalDateTime.MIN;

        public Builder(UUID customerId, String email, LocalDateTime createdAt) {
            this.customerId = customerId;
            this.email = email;
            this.createdAt = createdAt;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastLoginAt(LocalDateTime lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    public Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.name = builder.name;
        this.email = builder.email;
        this.lastLoginAt = builder.lastLoginAt;
        this.createdAt = builder.createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String changeName(String newName) {
        if (newName.isBlank()) {
            throw new RuntimeException("Name should not be a black");
        }
        name = newName;
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId.equals(customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}
