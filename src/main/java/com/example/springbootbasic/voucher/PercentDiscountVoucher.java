package com.example.springbootbasic.voucher;

public class PercentDiscountVoucher implements Voucher {

    private final long percent;

    public PercentDiscountVoucher(long percent) {
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (percent / 100);
    }

    public long getPercent() {
        return percent;
    }
}
