package com.devcourse.springbootbasic.application.customer.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final boolean isBlack;

    public Customer(UUID customerId, String name, boolean isBlack) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.isBlack = isBlack;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return isBlack == customer.isBlack && Objects.equals(customerId, customer.customerId) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, isBlack);
    }

    @Override
    public String toString() {
        return MessageFormat.format("Customer(id: {0}, name: {1}, isBlack: {2})", customerId, name, isBlack);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public boolean isBlack() {
        return isBlack;
    }

}
