package com.programmers.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long discountPercent;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) {
        validateZeroDiscount(discountPercent);
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
    }

    private void validateZeroDiscount(long discountPercent) {
        if (discountPercent == 0) {
            throw new IllegalArgumentException("할인 0%는 불가합니다.");
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - (discountPercent / 100.0)));
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%d%%", VoucherType.PERCENT_DISCOUNT_VOUCHER, voucherId, discountPercent);
    }
}
