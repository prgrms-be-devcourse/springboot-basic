package com.programmers.springbootbasic.service.dto;

import java.time.LocalDateTime;

public record VoucherRequest(
        String voucherType,
        String name,
        Long minimumPriceCondition,
        LocalDateTime createdDate,
        LocalDateTime expirationDate,
        int amountOrPercent
) {
}
