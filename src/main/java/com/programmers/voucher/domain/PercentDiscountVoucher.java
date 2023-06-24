package com.programmers.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{

    public PercentDiscountVoucher(UUID voucherId, long discountAmount) {
        super(voucherId, discountAmount);
    }

    @Override
    public long discount(long originPrice) {
        return originPrice - (originPrice * getDiscountAmount() / 100);
    }

}
