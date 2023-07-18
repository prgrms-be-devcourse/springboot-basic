package com.tangerine.voucher_system.application.customer.service.dto;

import com.tangerine.voucher_system.application.customer.model.Name;

import java.util.UUID;

public record CustomerResult(
        UUID customerId,
        Name name,
        boolean isBlack
) {
}
