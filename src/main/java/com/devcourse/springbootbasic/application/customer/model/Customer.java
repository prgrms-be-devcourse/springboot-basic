package com.devcourse.springbootbasic.application.customer.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public Customer(
            UUID customerId, String name, String email, LocalDateTime createdAt
    ) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        validateName();
        validateEmail();
    }

    private void validateName() {
        if (name.isBlank()) {
            throw new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_INFO.getMessageText());
        }
    }

    private void validateEmail() {
        if (!email.contains("@")) {
            throw new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_INFO.getMessageText());
        }
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "Customer(id: {0}, name: {1}, email: {2}, createAt: {3})"
                , customerId, name, email, createdAt);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
