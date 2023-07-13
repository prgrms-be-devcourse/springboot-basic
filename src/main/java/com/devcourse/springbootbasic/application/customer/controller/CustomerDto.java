package com.devcourse.springbootbasic.application.customer.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.text.MessageFormat;
import java.util.UUID;

public record CustomerDto(
        UUID customerId,
        String name,
        boolean isBlack
) {
    public static CustomerDto of(Customer entity) {
        return new CustomerDto(
                entity.getCustomerId(),
                entity.getName(),
                entity.isBlack()
        );
    }

    public static Customer to(CustomerDto dto) {
        validateName(dto.name);
        return new Customer(
                dto.customerId(),
                dto.name(),
                dto.isBlack());
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText());
        }
    }

    public String toString() {
        return MessageFormat.format(
                "Customer(id: {0}, name: {1}, isBlack: {2})",
                customerId, name, isBlack
        );
    }
}
