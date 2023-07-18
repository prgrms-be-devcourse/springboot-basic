package com.tangerine.voucher_system.application.customer.service.dto;

import com.tangerine.voucher_system.application.customer.model.Name;

import java.util.UUID;

public record CustomerParam(
        UUID customerId,
        Name name,
        boolean isBlack
) {
}
