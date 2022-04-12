package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessage;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;
    private static final Long MAX_DISCOUNT = 10000L;
    private static final Long MIN_DISCOUNT = 0L;
    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < MIN_DISCOUNT || amount > MAX_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + ErrorMessage.OUT_OF_RANGE_FIXED_NUMBER.getMessage());
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = VoucherType.FIXED;
    }

    public FixedAmountVoucher(long amount) {
        if (amount < MIN_DISCOUNT || amount > MAX_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + ErrorMessage.OUT_OF_RANGE_FIXED_NUMBER.getMessage());
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
        this.voucherType = VoucherType.FIXED;
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
        return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", voucherType=" + voucherType +
                '}';
    }
}
