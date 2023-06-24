package com.programmers.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        super(voucherId,discountAmount);
    }

    @Override
    public long discount(long originPrice) {
        return originPrice - getDiscountAmount();
    }

}
