package com.programmers.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t$%,d", VoucherType.FIXED_AMOUNT_VOUCHER, voucherId, amount);
    }
}
