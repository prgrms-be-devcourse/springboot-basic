package org.programmers.springbootbasic.application.customer.model;

import org.programmers.springbootbasic.core.exception.CustomInvalidNameException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new CustomInvalidNameException("Name should not be blank");
        }
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public String getCustomerName() {
        return this.name;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Customer that = (Customer) o;
        return customerId.equals(that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}
