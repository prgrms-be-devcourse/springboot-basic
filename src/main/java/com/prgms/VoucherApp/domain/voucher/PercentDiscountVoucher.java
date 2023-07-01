package com.prgms.VoucherApp.domain.voucher;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final Long PERCENT_CONVERT_VALUE = 100L;
    private final UUID voucherId;
    private final BigDecimal percent;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID voucherId, BigDecimal percent, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = voucherType;
    }

    @Override
    public BigDecimal discount(BigDecimal beforeAmount) {
        BigDecimal discountAmount = beforeAmount.multiply(percent)
                .divide(BigDecimal.valueOf(PERCENT_CONVERT_VALUE), MathContext.UNLIMITED);
        return beforeAmount.subtract(discountAmount);
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return this.percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
