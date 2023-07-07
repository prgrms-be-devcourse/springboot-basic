package com.programmers.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponseDto(
        UUID customerId,
        String name,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
