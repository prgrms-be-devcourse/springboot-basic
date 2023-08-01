package com.tangerine.voucher_system.application.customer.controller.dto;

public record CreateCustomerRequest(
        String name,
        boolean isBlack
) {
}