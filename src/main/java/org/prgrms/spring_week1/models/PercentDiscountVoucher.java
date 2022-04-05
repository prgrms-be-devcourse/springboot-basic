package org.prgrms.spring_week1.models;

public class PercentDiscountVoucher implements Voucher{
    private long percent;

    public PercentDiscountVoucher(int percent) {
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return 0;
    }
}
