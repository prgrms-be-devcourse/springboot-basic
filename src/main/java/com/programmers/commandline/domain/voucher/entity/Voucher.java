package com.programmers.commandline.domain.voucher.entity;

import java.util.UUID;

public abstract class Voucher {
    private String voucherId;
    private final VoucherType voucherType;

    protected Voucher(UUID uuid, VoucherType type) {
        this.voucherId = uuid.toString();
        this.voucherType = type;
    }

    public String getId() {
        return this.voucherId;
    }

    public String getType() {
        return this.voucherType.toString();
    }

    public abstract Long getDiscount(Long price);
}
