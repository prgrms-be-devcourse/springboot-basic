package com.blessing333.springbasic.voucher.domain;

public class FixedAmountVoucher extends Voucher{
    private final long discountAmount;

    public FixedAmountVoucher(long discountAmount){
        super();
        this.discountAmount = discountAmount;
    }

    @Override
    long discount(long beforePrice) {
        long discountedPrice = beforePrice - discountAmount;
        return discountedPrice > 0 ? discountedPrice : 0;
    }
}
