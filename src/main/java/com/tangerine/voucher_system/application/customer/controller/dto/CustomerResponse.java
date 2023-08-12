package com.tangerine.voucher_system.application.customer.controller.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID customerId,
        String name,
        boolean isBlack
) {
}
