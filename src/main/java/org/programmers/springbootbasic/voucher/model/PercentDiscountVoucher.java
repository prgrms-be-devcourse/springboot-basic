package org.programmers.springbootbasic.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < 0) throw new IllegalArgumentException("percent should be positive");
        if (percent > 99) throw new IllegalArgumentException("percent should be less than 100");
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher" +
                " voucherId: " + voucherId +
                " percent: " + percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}
