package com.tangerine.voucher_system.application.customer.controller.dto;

import com.tangerine.voucher_system.application.customer.model.Name;

import java.util.UUID;

public record CustomerResponse(
        UUID customerId,
        String name,
        boolean isBlack
) {
}
