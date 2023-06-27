package com.prgmrs.voucher.model;

import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

@Qualifier("fixed")
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

    public long getAmount() {
        return amount;
    }
    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
