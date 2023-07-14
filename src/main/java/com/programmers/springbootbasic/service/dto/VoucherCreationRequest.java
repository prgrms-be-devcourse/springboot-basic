package com.programmers.springbootbasic.service.dto;

import java.time.LocalDateTime;

public record VoucherCreationRequest(
        String voucherType,
        String name,
        Long minimumPriceCondition,
        LocalDateTime expirationDate,
        int amountOrPercent
        // 이 변수가 PercentDiscountPercent 의 percent, FixAmountVoucher 의 amount 두 가지를 의미를 담고 있는데 괜찮은가요?
) {
}
