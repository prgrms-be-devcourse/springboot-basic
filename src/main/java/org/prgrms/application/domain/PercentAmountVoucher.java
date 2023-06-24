package org.prgrms.application.domain;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher<Float> {
    private final UUID voucherId;
    private final float percent;

    public PercentAmountVoucher(UUID voucherId, float percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Float discount(Float beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "Percent { " + "voucherId=" + voucherId +", percent=" + percent + '}';
    }
}
