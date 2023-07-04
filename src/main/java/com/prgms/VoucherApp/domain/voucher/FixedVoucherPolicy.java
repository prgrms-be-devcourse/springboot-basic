package com.prgms.VoucherApp.domain.voucher;

import java.math.BigDecimal;

public class FixedVoucherPolicy implements VoucherPolicy {

    private static final Long DISCOUNT_AMOUNT_MIN_VALUE = 0L;
    private final BigDecimal fixedAmount;

    public FixedVoucherPolicy(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    @Override
    public BigDecimal discount(BigDecimal beforeAmount) {
        if (isResultNegative(beforeAmount)) {
            return BigDecimal.ZERO;
        }

        return beforeAmount.subtract(fixedAmount);
    }

    private boolean isResultNegative(BigDecimal beforeAmount) {
        return beforeAmount.subtract(fixedAmount)
            .compareTo(BigDecimal.ZERO) < DISCOUNT_AMOUNT_MIN_VALUE;
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return fixedAmount;
    }
}
