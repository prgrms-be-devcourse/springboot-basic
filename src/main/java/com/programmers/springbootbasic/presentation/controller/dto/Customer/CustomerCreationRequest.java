package com.programmers.springbootbasic.presentation.controller.dto.Customer;


public record CustomerCreationRequest(
        String name,
        String email
) {
}
