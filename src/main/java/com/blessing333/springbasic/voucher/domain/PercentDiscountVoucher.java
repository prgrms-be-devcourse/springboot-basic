package com.blessing333.springbasic.voucher.domain;

public class PercentDiscountVoucher extends Voucher{
    private final int percent;

    public PercentDiscountVoucher(int percent) {
        super();
        this.percent = percent;
    }

    @Override
    long discount(long beforePrice) {
        double discountRate = (100 - percent) / 100.0;
        return Math.round(beforePrice * discountRate);
    }
}
