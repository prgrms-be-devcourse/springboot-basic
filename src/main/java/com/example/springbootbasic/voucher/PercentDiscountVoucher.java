package com.example.springbootbasic.voucher;

public class PercentDiscountVoucher implements Voucher {

    private final long percent;

    public PercentDiscountVoucher(long percentNumber) {
        this.percent = percentNumber;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (percent / 100);
    }
}
