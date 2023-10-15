package com.weeklyMission.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{

    protected PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
    }

    @Override
    long discount(long beforeDiscount) {
        return beforeDiscount/amount;
    }
}
