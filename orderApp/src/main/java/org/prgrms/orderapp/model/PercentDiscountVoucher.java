package org.prgrms.orderapp.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {
    private static final long MAX_VOUCHER_AMOUNT = 100L;
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent <= 0) throw new IllegalArgumentException("percent should be positive");
        if (percent > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("percent should be less than " + MAX_VOUCHER_AMOUNT);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - percent / 100.0));
    }

    @Override
    public String toString() {
        return "PercentAmountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
