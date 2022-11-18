package org.programmers.springbootbasic.domain.customer.model;

import java.time.LocalDateTime;

public class Customer {
    private final long customerId;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public Customer(long id, String name, String email, LocalDateTime createdAt) {
        this.customerId = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "id : " + customerId + ", name : " + name + ", email : " + email;
    }

    public long getCustomerId() {
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
}
