package org.prgrms.kdt.kdtspringorder.voucher.domain;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 퍼센트 단위의 할인 바우처
 */
public class PercentDiscountVoucher extends Voucher {

    private static final long MAX_VOUCHER_PERCENT = 100;

    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        super(voucherId);
        super.voucherType = VoucherType.PERCENT;
        this.percent = percent;
        checkPercent(percent);
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, VoucherType voucherType, long percent, String useYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        super(voucherId, customerId, voucherType, useYn, createdAt, usedAt);
        this.percent = percent;
        checkPercent(percent);
    }

    private void checkPercent(long percent) {
        if(percent < 0) throw new IllegalArgumentException("Percent should be positive");
        if(percent == 0) throw new IllegalArgumentException("Percent should not be zero");
        if(percent > MAX_VOUCHER_PERCENT) throw new IllegalArgumentException(String.format("Percent should be less than %d %", MAX_VOUCHER_PERCENT));
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
                "voucherId=" + super.getVoucherId() +
                ", percent=" + percent +
                '}';
    }

}
