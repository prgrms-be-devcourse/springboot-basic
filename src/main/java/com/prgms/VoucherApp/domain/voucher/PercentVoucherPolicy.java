package com.prgms.VoucherApp.domain.voucher;

import java.math.BigDecimal;
import java.math.MathContext;

public class PercentVoucherPolicy implements VoucherPolicy {

    private static final Long PERCENT_CONVERT_VALUE = 100L;
    private final BigDecimal percent;

    public PercentVoucherPolicy(BigDecimal percent) {
        this.percent = percent;
    }

    @Override
    public BigDecimal discount(BigDecimal beforeAmount) {
        BigDecimal discountAmount = beforeAmount.multiply(percent)
            .divide(BigDecimal.valueOf(PERCENT_CONVERT_VALUE), MathContext.UNLIMITED);
        return beforeAmount.subtract(discountAmount);
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return percent;
    }
}
