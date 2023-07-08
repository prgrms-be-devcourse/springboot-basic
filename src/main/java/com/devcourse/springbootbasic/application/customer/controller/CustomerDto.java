package com.devcourse.springbootbasic.application.customer.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDto(
        UUID customerId,
        String name,
        String email,
        LocalDateTime createdTime
) {
    public static CustomerDto of(Customer entity) {
        return new CustomerDto(
                entity.getCustomerId(),
                entity.getName(),
                entity.getEmail(),
                entity.getcreatedTime()
        );
    }

    public static Customer to(CustomerDto dto) {
        return new Customer(
                dto.customerId(),
                dto.name(),
                dto.email(),
                dto.createdTime()
        );
    }

    public String toString() {
        return MessageFormat.format(
                "Customer(id: {0}, name: {1}, email: {2}, createdTime: {3})",
                customerId, name, email, createdTime
        );
    }
}
