package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private static final long MIN_AMOUNT = 0;
    private static final long MAX_AMOUNT = 100;
    private static final String OUT_OF_RANGE_AMOUNT = "할인 범위가 아닙니다.";

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long amount;

    public PercentAmountVoucher(UUID voucherId, VoucherType voucherType, long amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = validateAmount(amount);
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
    public long getAmount() {
        return amount;
    }

    @Override
    public long executeDiscount(long originPrice) {
        return originPrice * (amount / MAX_AMOUNT);
    }

    @Override
    public long validateAmount(long amount) {
        if (amount <= MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException(OUT_OF_RANGE_AMOUNT);
        }

        return amount;
    }
}
