package org.prgrms.kdt.model;

import org.prgrms.kdt.exception.InvalidDataException;

import java.io.Serializable;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validatePercent(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    private void validatePercent(long percent) {
        if(percent < 0 || percent > 100) {
            throw new InvalidDataException("invalid percentage: " + percent);
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long getPercent() {
        return percent;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}