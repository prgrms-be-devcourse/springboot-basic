package com.weeklyMission.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private static final long MAX_PERCENT_VOUCHER_AMOUNT = 99;
    private final UUID voucherId;
    private final long amount;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        if(amount<0) {
            throw new IllegalArgumentException("음수는 될 수 없음");
        }
        if(amount==0) {
            throw new ArithmeticException("0이 될 수 없음");
        }
        if(amount>MAX_PERCENT_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("%d보다 작아야 함".formatted(MAX_PERCENT_VOUCHER_AMOUNT));
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
        return (beforeDiscount-(beforeDiscount*amount/100));
    }
}
