package com.weeklyMission.voucher.domain;

import com.weeklyMission.exception.ExceptionMessage;

public class PercentDiscountVoucher extends Voucher{

    private static final long MAX_PERCENT_VOUCHER_AMOUNT = 99;

    public PercentDiscountVoucher(String voucherId, long amount) {
        if(amount<0) {
            throw new IllegalArgumentException(ExceptionMessage.CAN_NOT_ZERO.getMessage());
        }
        if(amount==0) {
            throw new IllegalArgumentException(ExceptionMessage.CAN_NOT_ZERO.getMessage());
        }
        if(amount>MAX_PERCENT_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("%d보다 작아야 함".formatted(MAX_PERCENT_VOUCHER_AMOUNT));
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
        return (beforeDiscount-(beforeDiscount*amount/100));
    }
}
