package com.programmers.commandline.domain.voucher.entity;

import java.util.UUID;

public abstract class Voucher {
    private String voucherId;
    private final Long discount;
    private final VoucherType voucherType;

    protected Voucher(UUID uuid, Long discount, VoucherType type) {
        this.voucherId = uuid.toString();
        this.discount = discount;
        this.voucherType = type;
    }

    public String getVoucherId() {
        return this.voucherId;
    }

    @Override
    public String toString() {
        return String.format("Id:%s Type:%s Discount:%d", this.voucherId, this.voucherType, this.discount);
    }
}
