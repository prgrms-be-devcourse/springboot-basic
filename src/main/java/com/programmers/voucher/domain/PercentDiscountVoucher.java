package com.programmers.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{

    private PercentDiscountVoucher(UUID voucherId, long discountAmount) {
        super(voucherId, discountAmount);
    }

    public static Voucher of(UUID voucherId, long discountAmount) {
        validate(voucherId, discountAmount);
        return new PercentDiscountVoucher(voucherId, discountAmount);
    }

    @Override
    public long discount(long originPrice) {
        return originPrice - (originPrice * getDiscountAmount() / 100);
    }

    private static void validate(UUID voucherId, long discountAmount) {

    }

}
