package org.devcourse.voucher.application.customer.model;

import java.util.UUID;

import static org.devcourse.voucher.core.exception.ErrorType.NAME_NOT_VALID_RANGE;
import static org.devcourse.voucher.core.exception.ErrorType.NOT_FOUND_NAME;

public class Customer {
    private final UUID customerId;
    private String name;
    private Email email;

    public Customer(UUID customerId, String name, Email email) {
        validateName(name);
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
            throw new IllegalArgumentException(NOT_FOUND_NAME.message());
        }
        if (name.length() > 0 && name.length() <= 25) {
            throw new IllegalArgumentException(NAME_NOT_VALID_RANGE.message());
        }
    }

    @Override
    public String toString() {
        return customerId + "\t" + name;
    }
}
