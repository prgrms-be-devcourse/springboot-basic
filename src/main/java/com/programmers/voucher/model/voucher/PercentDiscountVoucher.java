package com.programmers.voucher.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private static final String WRONG_DISCOUNT = "할인 퍼센트를 1~100사이로 입력해주세요.";

    public PercentDiscountVoucher(UUID voucherId, long discountValue) {
        super(voucherId, discountValue);
    }

    @Override
    protected void validateZeroDiscount(long discountValue) {
        if (discountValue <= 0 || discountValue > 100) {
            throw new IllegalArgumentException(WRONG_DISCOUNT);
        }
    }

    @Override
    public long discount(long fullAmount) {
        return (long) (fullAmount * (1 - (discountValue / 100.0)));
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%d%%", VoucherType.PERCENT_DISCOUNT_VOUCHER, voucherId, discountValue);
    }
}
