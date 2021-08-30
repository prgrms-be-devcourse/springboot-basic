package org.prgrms.kdt.kdtspringorder.voucher.domain;

import java.io.Serializable;
import java.util.UUID;

/**
 * 퍼센트 단위의 할인 바우처
 */
public class PercentDiscountVoucher implements Voucher, Serializable {

    private static final long MAX_VOUCHER_PERCENT = 100;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        checkPercent(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    private void checkPercent(long percent) {
        if(percent < 0) throw new IllegalArgumentException("Percent should be positive");
        if(percent == 0) throw new IllegalArgumentException("Percent should not be zero");
        if(percent > MAX_VOUCHER_PERCENT) throw new IllegalArgumentException(String.format("Percent should be less than %d %", MAX_VOUCHER_PERCENT));
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long getPercent() {
        return percent;
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
