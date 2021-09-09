package org.prgrms.kdt.kdtspringorder.voucher.domain;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 퍼센트 단위의 할인 바우처
 */
public class PercentDiscountVoucher extends Voucher {

    private static final long MAX_VOUCHER_PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
        super.voucherType = VoucherType.PERCENT;
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, long amount) {
        super(voucherId, customerId, amount);
        super.voucherType = VoucherType.PERCENT;
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, VoucherType voucherType, long amount, boolean useYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        super(voucherId, customerId, voucherType, amount, useYn, createdAt, usedAt);
        checkPercent(amount);
    }

    private void checkPercent(long percent) {
        if(percent < 0) throw new IllegalArgumentException("Percent should be positive");
        if(percent == 0) throw new IllegalArgumentException("Percent should not be zero");
        if(percent > MAX_VOUCHER_PERCENT) throw new IllegalArgumentException(String.format("Percent should be less than %d %", MAX_VOUCHER_PERCENT));
    }


    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (super.getAmount() / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + super.getVoucherId() +
                ", percent=" + super.getAmount() +
                '}';
    }

}
