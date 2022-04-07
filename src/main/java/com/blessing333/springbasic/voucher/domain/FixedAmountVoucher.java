package com.blessing333.springbasic.voucher.domain;

public class FixedAmountVoucher extends Voucher{
    private final long amount;

    public FixedAmountVoucher(long amount){
        super();
        this.amount = amount;
    }

    @Override
    long discount(long beforePrice) {
        long discountedPrice = beforePrice - amount;
        return discountedPrice > 0 ? discountedPrice : 0;
    }
}
