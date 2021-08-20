package com.programmers.voucher.entity.voucher;

public class PercentDiscountVoucher extends Voucher {

    private double percentage;

    public PercentDiscountVoucher(long id, String name, double percentage) {
        super(id, name);
        this.percentage = percentage;
    }

    public PercentDiscountVoucher(String name, double percentage) {
        super(name);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return String.format("[Voucher #%d] %s / %f%% / PERCENT", super.id, super.name, percentage * 100);
    }

    @Override
    public int discount(int original) {
        return (int)(original * percentage);
    }
}
