package com.prgrms.management.voucher.domain;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;

    public PercentAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = VoucherType.PERCENT;
    }

    public PercentAmountVoucher(long amount) {
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