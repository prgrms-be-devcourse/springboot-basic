package com.programmers.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private FixedAmountVoucher(UUID voucherId, long discountAmount) {
        super(voucherId,discountAmount);
    }

    public static Voucher of(UUID voucherId, long discountAmount) {
        validate(voucherId, discountAmount);
        return new FixedAmountVoucher(voucherId, discountAmount);
    }

    @Override
    public long discount(long originPrice) {
        return originPrice - getDiscountAmount();
    }

    private static void validate(UUID voucherId, long discountAmount) {

    }

}
