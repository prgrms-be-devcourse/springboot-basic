package com.prgrms.devkdtorder.voucher.domain;

import com.prgrms.devkdtorder.voucher.domain.Voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validatePercent(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - percent/(double)100));
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }

    private void validatePercent(long percent) {
        if (percent < 0) throw new IllegalArgumentException("Percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("Percent should not be zero");
        if (percent > 100) throw new IllegalArgumentException("Percent should be less than 100");
    }
}

