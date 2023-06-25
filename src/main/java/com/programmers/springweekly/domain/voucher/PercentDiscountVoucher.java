package com.programmers.springweekly.domain.voucher;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PercentDiscountVoucher implements Voucher{

    private final UUID voucherId;
    private final long discountPercent;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (discountPercent / 100);
    }

    @Override
    public long getVoucherAmount() {
        return discountPercent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }
}
