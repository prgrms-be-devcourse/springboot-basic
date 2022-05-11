package org.programmers.kdtspring.dto;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.user.Email;
import org.programmers.kdtspring.entity.user.Name;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDTO(
        UUID customerId,
        Name name,
        Email email,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt
) {
    static CustomerDTO of(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt(),
                customer.getCreatedAt());
    }

    static Customer to(CustomerDTO dto) {
        return new Customer(
                dto.customerId(),
                dto.name().toString(),
                dto.email().toString(),
                dto.createdAt());
    }
}