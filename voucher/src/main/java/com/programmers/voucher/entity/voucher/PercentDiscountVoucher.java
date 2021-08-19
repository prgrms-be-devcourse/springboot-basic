package com.programmers.voucher.entity.voucher;

public class PercentDiscountVoucher extends Voucher {

    double percentage;

    public PercentDiscountVoucher(long id, String name, double percentage) {
        super(id, name);
        this.percentage = percentage;
    }

    @Override
    public int discount(int original) {
        return (int)(original * percentage);
    }
}
