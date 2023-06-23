package com.programmers.springweekly.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long fixedDiscountAmount;
    private final String voucherType;

    public FixedAmountVoucher(UUID voucherId, long fixedDiscountAmount, String voucherType) {
        this.voucherId = voucherId;
        this.fixedDiscountAmount = fixedDiscountAmount;
        this.voucherType = voucherType;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - fixedDiscountAmount;
    }

    @Override
    public long getVoucherAmount() {
        return fixedDiscountAmount;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }
}
