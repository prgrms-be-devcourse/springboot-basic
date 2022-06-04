package org.devcourse.voucher.application.customer.model;

import java.util.UUID;

import static org.devcourse.voucher.core.exception.ErrorType.*;

public class Customer {
    private final UUID customerId;
    private String name;
    private Email email;

    public Customer(UUID customerId, String name, Email email) {
        validateId(customerId);
        validateName(name);
        validateEmail(email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException(DATA_IS_NULL.message());
        }
        if (name.length() <= 0 || name.length() > 25) {
            throw new IllegalArgumentException(NAME_NOT_VALID_RANGE.message());
        }
    }
    private void validateEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException(DATA_IS_NULL.message());
        }
    }

    private void validateId(UUID customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException(DATA_IS_NULL.message());
        }
    }
    @Override
    public String toString() {
        return customerId + "\t" + name;
    }
}
