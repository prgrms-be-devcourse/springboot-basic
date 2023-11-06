package com.weeklyMission.voucher.domain;

import com.weeklyMission.exception.ExceptionMessage;

public class FixedAmountVoucher extends Voucher{
    private static final long MAX_FIXED_VOUCHER_AMOUNT = 10000;

    public FixedAmountVoucher(String voucherId, long amount) {
        if(amount<0) {
            throw new IllegalArgumentException(ExceptionMessage.CAN_NOT_ZERO.getMessage());
        }
        if(amount==0) {
            throw new IllegalArgumentException(ExceptionMessage.CAN_NOT_ZERO.getMessage());
        }
        if(amount>MAX_FIXED_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("%d보다 작아야 함".formatted(MAX_FIXED_VOUCHER_AMOUNT));
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public String getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return Math.max(0, beforeDiscount-amount);
    }
}
