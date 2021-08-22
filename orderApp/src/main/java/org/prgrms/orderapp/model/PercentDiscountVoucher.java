package org.prgrms.orderapp.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (percent / 100.0));
    }

    @Override
    public String toString() {
        return "PercentAmountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
