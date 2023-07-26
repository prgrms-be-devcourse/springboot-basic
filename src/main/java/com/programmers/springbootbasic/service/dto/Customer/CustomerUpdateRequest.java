package com.programmers.springbootbasic.service.dto.Customer;

public record CustomerUpdateRequest(
        String name,
        String email
) {
}
