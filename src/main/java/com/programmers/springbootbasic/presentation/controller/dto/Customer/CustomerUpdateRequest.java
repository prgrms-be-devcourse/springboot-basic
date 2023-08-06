package com.programmers.springbootbasic.presentation.controller.dto.Customer;

public record CustomerUpdateRequest(
        String name,
        String email
) {
}
