package com.prgms.VoucherApp.domain.voucher;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private static final Long PERCENT_CONVERT_VALUE = 100L;

    public PercentDiscountVoucher(UUID getVoucherId, BigDecimal discountAmount) {
        super(getVoucherId, discountAmount);
    }

    @Override
    public BigDecimal discount(BigDecimal beforeAmount) {
        BigDecimal discountAmount = beforeAmount.multiply(amount)
            .divide(BigDecimal.valueOf(PERCENT_CONVERT_VALUE), MathContext.UNLIMITED);
        return beforeAmount.subtract(discountAmount);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT_VOUCHER;
    }
}
