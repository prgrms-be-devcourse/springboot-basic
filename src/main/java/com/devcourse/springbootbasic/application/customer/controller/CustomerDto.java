package com.devcourse.springbootbasic.application.customer.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;

import java.text.MessageFormat;
import java.util.UUID;

public record CustomerDto(
        UUID customerId,
        String name
) {
    public static CustomerDto of(Customer entity) {
        return new CustomerDto(
                entity.getCustomerId(),
                entity.getName()
        );
    }

    public static Customer to(CustomerDto dto) {
        return new Customer(
                dto.customerId(),
                dto.name()
        );
    }

    public String toString() {
        return MessageFormat.format(
                "Customer(id: {0}, name: {1})",
                customerId, name
        );
    }
}
