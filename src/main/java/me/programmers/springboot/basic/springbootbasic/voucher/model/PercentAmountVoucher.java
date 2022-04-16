package me.programmers.springboot.basic.springbootbasic.voucher.model;

import java.util.UUID;

public class PercentAmountVoucher extends Voucher {

    private final long percent;
    private final UUID voucherId;

    public PercentAmountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        double discountRange = percent / 100;
        long discountedPrice = beforeDiscount - (long) (beforeDiscount * discountRange);
        return discountedPrice;
    }

    @Override
    public String toString() {
        return "PercentAmountVoucher{" +
                "percent=" + percent +
                ", voucherId=" + voucherId +
                '}';
    }
}
