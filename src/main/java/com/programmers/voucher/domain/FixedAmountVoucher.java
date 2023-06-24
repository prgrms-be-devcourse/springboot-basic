package com.programmers.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final int MAX_DISCOUNT_AMOUNT = 5000;
    private static final int MIN_DISCOUNT_AMOUNT = 0;

    private FixedAmountVoucher(UUID voucherId, long discountAmount) {
        super(voucherId,discountAmount);
    }

    public static Voucher of(UUID voucherId, long discountAmount) {
        validateDiscountAmount(discountAmount);
        return new FixedAmountVoucher(voucherId, discountAmount);
    }

    @Override
    public long discount(long originPrice) {
        if (originPrice < getDiscountAmount()) {
            return 0;
        }
        return originPrice - getDiscountAmount();
    }

    private static void validateDiscountAmount(long discountAmount) {
        if (discountAmount > MAX_DISCOUNT_AMOUNT || discountAmount < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("1 ~ 5000 범위의 바우처 할인양을 입력해주세요");
        }
    }

}
