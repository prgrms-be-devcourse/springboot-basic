package com.programmers.springweekly.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountPercent;

    public PercentDiscountVoucher(long discountPercent) {
        this.voucherId = UUID.randomUUID();
        this.discountPercent = discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount - beforeDiscount * ((double) discountPercent / 100));
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
