package com.example.vouchermanager.domain;

public class PercentAmountVoucher implements Voucher {

    private final long percent;

    public PercentAmountVoucher(long percent) {
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (percent / 100);
    }
}
