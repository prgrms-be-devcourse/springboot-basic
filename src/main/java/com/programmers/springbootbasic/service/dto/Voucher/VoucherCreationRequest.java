package com.programmers.springbootbasic.service.dto.Voucher;

import java.time.LocalDateTime;

public record VoucherCreationRequest(
        String voucherType,
        String name,
        Long minimumPriceCondition,
        LocalDateTime expirationDate,
        int amountOrPercent
) {
}
