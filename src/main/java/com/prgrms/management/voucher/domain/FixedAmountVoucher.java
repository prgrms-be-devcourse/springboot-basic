package com.prgrms.management.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;

    public FixedAmountVoucher(long amount) {
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
        this.voucherType=VoucherType.FIXED;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
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
