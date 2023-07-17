package com.prgrms.dto.customer;

import com.prgrms.model.customer.Customer;

import java.time.LocalDateTime;

public record CustomerResponse(
        String email,
        LocalDateTime createdAt,
        String name,
        LocalDateTime lastLoginAt
) {

    public CustomerResponse(Customer customer) {
        this(
                customer.getEmail(),
                customer.getCreatedAt(),
                customer.getName().getValue(),
                customer.getLastLoginAt()
        );
    }

    @Override
    public String toString() {
        return "email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", lastLoginAt=" + lastLoginAt ;
    }
}
