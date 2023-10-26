package com.weeklyMission.voucher.domain;

public class PercentDiscountVoucher extends Voucher{

    private static final long MAX_PERCENT_VOUCHER_AMOUNT = 99;

    public PercentDiscountVoucher(Long voucherId, long amount) {
        if(amount<0) {
            throw new IllegalArgumentException("음수는 될 수 없음");
        }
        if(amount==0) {
            throw new IllegalArgumentException("0이 될 수 없음");
        }
        if(amount>MAX_PERCENT_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("%d보다 작아야 함".formatted(MAX_PERCENT_VOUCHER_AMOUNT));
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getAmount() {
        return amount;
    }

    @Override
    public Long discount(long beforeDiscount) {
        return (beforeDiscount-(beforeDiscount*amount/100));
    }
}
