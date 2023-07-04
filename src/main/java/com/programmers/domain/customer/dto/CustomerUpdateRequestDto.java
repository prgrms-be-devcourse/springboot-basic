package com.programmers.domain.customer.dto;

import com.programmers.domain.customer.CustomerType;

import java.util.UUID;

public record CustomerUpdateRequestDto(UUID id, String name, CustomerType type) {

}
