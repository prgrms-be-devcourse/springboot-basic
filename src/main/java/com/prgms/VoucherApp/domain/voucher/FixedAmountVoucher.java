package com.prgms.VoucherApp.domain.voucher;

import java.math.BigDecimal;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final Long DISCOUNT_AMOUNT_MIN_VALUE = 0L;
    private final UUID voucherId;
    private final BigDecimal fixedAmount;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, BigDecimal fixedAmount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.fixedAmount = fixedAmount;
        this.voucherType = voucherType;
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
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return this.fixedAmount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
