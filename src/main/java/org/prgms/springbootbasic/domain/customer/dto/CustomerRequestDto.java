package org.prgms.springbootbasic.domain.customer.dto;

public record CustomerRequestDto (
        String customerId,
        String name,
        String email,
        String createdAt,
        String lastLoginAt,
        String isBlacked
) {
}
