package com.weeklyMission.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher{

    protected FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
    }

    @Override
    long discount(long beforeDiscount) {
        return beforeDiscount-amount;
    }
}
