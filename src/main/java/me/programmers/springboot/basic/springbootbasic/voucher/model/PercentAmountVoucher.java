package me.programmers.springboot.basic.springbootbasic.voucher.model;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {

    private final long percent;
    private final UUID voucherId;

    public PercentAmountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount - beforeDiscount * ((double) percent / 100));
    }

    @Override
    public String toString() {
        return "PercentAmountVoucher{" +
                "percent=" + percent +
                ", voucherId=" + voucherId +
                '}';
    }
}
