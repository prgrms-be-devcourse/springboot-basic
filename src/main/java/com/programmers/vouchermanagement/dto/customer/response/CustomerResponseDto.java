package com.programmers.vouchermanagement.dto.customer.response;

import java.util.UUID;

public record CustomerResponseDto(UUID id, String email) {

    public static CustomerResponseDto from(UUID id, String email) {
        return new CustomerResponseDto(id, email);
    }
}
