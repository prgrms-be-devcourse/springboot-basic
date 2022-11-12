package com.programmers.voucher.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private final String ZERO_DISCOUNT = "할인 0%는 불가합니다.";

    public PercentDiscountVoucher(UUID voucherId, long discountValue) {
        super(voucherId, discountValue);
    }

    @Override
    protected void validateZeroDiscount(long discountValue) {
        if (discountValue == 0) {
            throw new IllegalArgumentException(ZERO_DISCOUNT);
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - (discountValue / 100.0)));
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%d%%", VoucherType.PERCENT_DISCOUNT_VOUCHER, voucherId, discountValue);
    }
}
