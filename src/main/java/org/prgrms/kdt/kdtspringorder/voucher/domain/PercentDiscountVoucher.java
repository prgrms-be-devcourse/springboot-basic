package org.prgrms.kdt.kdtspringorder.voucher.domain;

import java.io.Serializable;
import java.util.UUID;

/**
 * 퍼센트 단위의 할인 바우처
 */
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
