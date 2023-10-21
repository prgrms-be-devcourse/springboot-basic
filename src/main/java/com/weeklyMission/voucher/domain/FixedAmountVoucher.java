package com.weeklyMission.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher{
    private static final long MAX_FIXED_VOUCHER_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if(amount<0) {
            throw new IllegalArgumentException("음수는 될 수 없음");
        }
        if(amount==0) {
            throw new IllegalArgumentException("0이 될 수 없음");
        }
        if(amount>MAX_FIXED_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("%d보다 작아야 함".formatted(MAX_FIXED_VOUCHER_AMOUNT));
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount-amount<0?0:beforeDiscount-amount;
    }
}
