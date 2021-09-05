package org.prgrms.kdt.service.dto;

import org.prgrms.kdt.domain.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class RequestCreateCustomerDto {

    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public RequestCreateCustomerDto(String name, String email, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer toEntity(UUID userId) {
        return new Customer(userId, name, email, createdAt);
    }
}
