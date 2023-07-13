package com.devcourse.springbootbasic.application.customer.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.text.MessageFormat;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;

    public Customer(
            UUID customerId, String name
    ) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText());
        }
    }

    @Override
    public String toString() {
        return MessageFormat.format("Customer(id: {0}, name: {1})", customerId, name);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

}
