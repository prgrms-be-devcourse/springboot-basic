package org.prgrms.application.domain;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final double percent;

    public PercentAmountVoucher(UUID voucherId, double percent) {
        if (percent <= 0 || percent >= 100) throw new IllegalArgumentException("잘못된 입력 범위입니다.");
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public double discount(double beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "Percent { " + "voucherId=" + voucherId + ", percent=" + percent + '}';
    }

}
