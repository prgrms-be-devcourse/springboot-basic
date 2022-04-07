package com.blessing333.springbasic.voucher.domain;

public class PercentDiscountVoucher extends Voucher{
    private final int percent;

    public PercentDiscountVoucher(int percent) {
        super();
        this.percent = percent;
    }

    @Override
    long discount(long beforePrice) {
        return beforePrice * (percent/100);
    }
}
