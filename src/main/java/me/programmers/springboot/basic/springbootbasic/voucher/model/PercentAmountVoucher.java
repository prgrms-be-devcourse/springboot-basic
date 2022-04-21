package me.programmers.springboot.basic.springbootbasic.voucher.model;

import java.util.UUID;

public class PercentAmountVoucher extends Voucher {

    private final long percent;

    public PercentAmountVoucher(UUID voucherId, long percent) {
        super(voucherId);
        this.percent = percent;
    }

    public long getPercent() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        double discountBase = 100;
        double discountRange = percent / discountBase;
        long discountedPrice = beforeDiscount - (long) (beforeDiscount * discountRange);
        return discountedPrice;
    }

    @Override
    public String toString() {
        return "PercentAmountVoucher{" +
                "voucherId=" + super.getVoucherId() +
                ", percent=" + percent +
                '}';
    }
}
