package org.prgrms.kdtspringhomework.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
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
        return (long) (beforeDiscount * ((double) percent / 100));
    }

    @Override
    public String toString() {
        return "PercentDiscount[" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                "]";
    }
}
