package org.prgms.springbootbasic.controller.customer.dto;

public record CustomerRequestDto (
        String customerId,
        String name,
        String email,
        String createdAt,
        String lastLoginAt,
        String isBlacked
) {
}
