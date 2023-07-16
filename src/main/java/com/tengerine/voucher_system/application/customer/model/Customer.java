package com.tengerine.voucher_system.application.customer.model;

import com.tengerine.voucher_system.application.global.exception.ErrorMessage;
import com.tengerine.voucher_system.application.global.exception.InvalidDataException;

import java.text.MessageFormat;
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
