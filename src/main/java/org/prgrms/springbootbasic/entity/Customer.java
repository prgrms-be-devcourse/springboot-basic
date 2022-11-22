package org.prgrms.springbootbasic.entity;

import java.time.LocalDateTime;
import java.util.UUID;


public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.name = builder.name;
        this.email = builder.email;
        this.createdAt = builder.createdAt;
        this.lastLoginAt = builder.lastLoginAt;
    }

    public static class Builder {
        private UUID customerId;
        private String name;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime lastLoginAt;

        public Builder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
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

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{ customer : customerId - "+ customerId + ", name - " + name + ", email - " + email +"}";
    }
}
