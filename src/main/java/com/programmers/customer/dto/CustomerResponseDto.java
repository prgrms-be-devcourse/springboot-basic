package com.programmers.customer.dto;

import com.programmers.customer.domain.CustomerType;

import java.util.UUID;

public record CustomerResponseDto(UUID id, String name, CustomerType type) {

}
