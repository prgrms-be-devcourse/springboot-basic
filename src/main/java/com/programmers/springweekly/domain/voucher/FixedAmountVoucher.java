package com.programmers.springweekly.domain.voucher;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class FixedAmountVoucher implements Voucher{

    private final UUID voucherId;
    private final long fixedDiscountAmount;

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
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }
}
