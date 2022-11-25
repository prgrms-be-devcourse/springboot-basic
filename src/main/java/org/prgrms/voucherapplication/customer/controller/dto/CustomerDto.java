package org.prgrms.voucherapplication.customer.controller.dto;

import org.prgrms.voucherapplication.customer.entity.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {
    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime lastLoginAt;
    private final LocalDateTime createAt;

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createAt = createAt;
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    static CustomerDto of(Customer customer) {
        return new CustomerDto(customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt(),
                customer.getCreateAt());
    }

    public static Customer to(CustomerDto dto) {
        return new Customer(dto.getCustomerId(),
                dto.getName(),
                dto.getEmail(),
                dto.getLastLoginAt(),
                dto.getCreateAt());
    }

}
