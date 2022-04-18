package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessageType;

import java.util.UUID;

public class PercentAmountVoucher implements VoucherPolicy {
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;
    private static final Long MAX_DISCOUNT = 100L;
    private static final Long MIN_DISCOUNT = 0L;

    public PercentAmountVoucher(UUID voucherId, long amount) {
        if (amount < MIN_DISCOUNT || amount > MAX_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + ErrorMessageType.OUT_OF_RANGE_PERCENT_NUMBER.getMessage());
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = VoucherType.PERCENT;
    }

    public PercentAmountVoucher(long amount) {
        if (amount < MIN_DISCOUNT || amount > MAX_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + ErrorMessageType.OUT_OF_RANGE_PERCENT_NUMBER.getMessage());
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
        this.voucherType = VoucherType.PERCENT;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    @Override
    public long getAmount() {
        return this.amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }

    @Override
    public String toString() {
        return "PercentAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", voucherType=" + voucherType +
                '}';
    }
}