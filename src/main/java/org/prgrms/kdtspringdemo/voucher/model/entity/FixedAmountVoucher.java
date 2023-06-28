package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MIN_DISCOUNT = 0;
    private static final String OUT_OF_RANGE_DISCOUNT = "할인 범위가 아닙니다.";
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long discount;

    public FixedAmountVoucher(UUID voucherId, VoucherType voucherType, long discount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = validateDiscount(discount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getDiscount() {
        return discount;
    }

    @Override
    public long executeDiscount(long beforeDiscount) {
        return beforeDiscount - discount;
    }

    @Override
    public long validateDiscount(long discount) {
        if (discount <= MIN_DISCOUNT) {
            throw new IllegalArgumentException(OUT_OF_RANGE_DISCOUNT);
        }
        return discount;
    }
}
