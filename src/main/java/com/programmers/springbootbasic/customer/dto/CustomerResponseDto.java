package com.programmers.springbootbasic.customer.dto;

import com.programmers.springbootbasic.customer.domain.CustomerType;

import java.util.UUID;

public record CustomerResponseDto(UUID id, String name, CustomerType type) {

}
