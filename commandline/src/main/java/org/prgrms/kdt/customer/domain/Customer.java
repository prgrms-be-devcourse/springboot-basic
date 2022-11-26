package org.prgrms.kdt.customer.domain;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.customer.NotNullNameException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Customer {
    private final Long customerId;
    private String name;
    private final String email;
    private final LocalDateTime createdAt;

    public Customer(Long customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new NotNullNameException(ErrorCode.NOT_NULL_NAME_EXCEPTION.getMessage());
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getCustomerId(), customer.getCustomerId()) && Objects.equals(getName(), customer.getName()) && Objects.equals(getEmail(), customer.getEmail()) && Objects.equals(createdAt, customer.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getName(), getEmail(), createdAt);
    }
}
