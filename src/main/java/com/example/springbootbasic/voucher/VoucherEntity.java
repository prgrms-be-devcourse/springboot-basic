package com.example.springbootbasic.voucher;

import java.util.UUID;

public class VoucherEntity {
    private final UUID voucherId;
    private final long amount;
    private final String voucherType;

    public VoucherEntity(UUID voucherId, long amount, String voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public String getVoucherType() {
        return voucherType;
    }
}
