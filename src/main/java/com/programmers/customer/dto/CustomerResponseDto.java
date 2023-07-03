package com.programmers.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

//4차 PR
public record CustomerResponseDto(
        UUID customerId,
        String name,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
