package org.prgrms.java.service.mapper;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.badrequest.CustomerBadRequestException;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerMapper {
    private CustomerMapper() {
    }

    public static Customer mapToCustomer(String line) {
        String[] fields = line.split(",");
        if (fields.length != 5) {
            throw new CustomerBadRequestException("손상된 사용자 데이터입니다.");
        }

        UUID customerId = UUID.fromString(fields[0].trim());
        String name = fields[1].trim();
        String email = fields[2].trim();
        LocalDateTime createdAt = LocalDateTime.parse(fields[3].trim());
        boolean isBlocked = Boolean.parseBoolean(fields[4].trim());

        return Customer.builder()
                .customerId(customerId)
                .name(name)
                .email(email)
                .createdAt(createdAt)
                .isBlocked(isBlocked)
                .build();
    }
}
