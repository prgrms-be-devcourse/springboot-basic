package com.pgms.part1.domain.customer.dto;

import java.util.UUID;

public record CustomerResponseDto(UUID id, Boolean isBlocked) {
}
