package com.pgms.part1.domain.customer.dto;

public record CustomerResponseDto(Long id, String name, String email, Boolean isBlocked) {
}
