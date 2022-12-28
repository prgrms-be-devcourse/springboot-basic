package org.programmers.weekly.mission.domain.voucher.model;

import java.io.Serializable;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = getPercent(percent);
    }

    private long getPercent(long percent) {
        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }
        return percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public long getDiscount() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount * (1 - percent * 0.01));
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
