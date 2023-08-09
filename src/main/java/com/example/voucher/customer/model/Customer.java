package com.example.voucher.customer.model;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.voucher.constant.CustomerType;
import com.example.voucher.query.marker.Entity;

public class Customer implements Entity {

    private final UUID customerId;
    private final String name;
    private final String email;
    private final CustomerType customerType;
    private final LocalDateTime createdAt;

    private Customer(UUID customerId, String name, String email, CustomerType customerType, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.customerType = customerType;
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID customerId = UUID.randomUUID();
        private String name;
        private String email;
        private CustomerType customerType;
        private LocalDateTime createdAt = LocalDateTime.now();

        public Builder() {
        }

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

        public Builder customerType(CustomerType customerType) {
            this.customerType = customerType;

            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;

            return this;
        }

        public Customer build() {
            return new Customer(customerId, name, email, customerType, createdAt);
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

    public CustomerType getCustomerType() {
        return customerType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
