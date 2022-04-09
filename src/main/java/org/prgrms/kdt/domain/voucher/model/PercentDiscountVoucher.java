package org.prgrms.kdt.domain.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final int percent;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        if(percent > 100) throw new IllegalArgumentException("할인율은 100퍼센트를 초과할 수 없습니다.");
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
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
