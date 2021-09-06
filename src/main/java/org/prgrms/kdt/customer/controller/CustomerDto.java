package org.prgrms.kdt.customer.controller;

import org.prgrms.kdt.customer.model.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

// 불변
public record CustomerDto(
        UUID customerId,
        String name,
        String email,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt
) {
    static CustomerDto of(Customer customer) {
        return new CustomerDto(customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt(),
                customer.getCreatedAt());
    }

    static Customer to(CustomerDto dto) {
        return new Customer(dto.customerId(),
                dto.name(),
                dto.email(),
                dto.lastLoginAt(),
                dto.createdAt());
    }
}
