package com.tangerine.voucher_system.application.customer.controller.dto;

import com.tangerine.voucher_system.application.customer.model.Name;

public record CreateCustomerRequest(
        String name,
        boolean isBlack
) {
}