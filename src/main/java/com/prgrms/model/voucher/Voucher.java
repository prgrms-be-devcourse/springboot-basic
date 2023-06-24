package com.prgrms.model.voucher;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public abstract class Voucher {
    private UUID voucherId;
    private long amount;
    private String voucherType;

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getVoucherDiscount() {
        return amount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    abstract public long discount(long beforeDiscount);
}
