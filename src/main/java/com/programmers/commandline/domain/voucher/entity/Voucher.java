package com.programmers.commandline.domain.voucher.entity;

import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    private final Long discount;
    private final VoucherType vouchertype;
    private final String unit;

    protected Voucher(UUID voucherId, Long discount, VoucherType vouchertype, String unit) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.vouchertype = vouchertype;
        this.unit = unit;
    }

    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public String toString() {
        return String.format("Id: %s Type: %s Discount: %d %s", this.voucherId, this.vouchertype, this.discount, this.unit);
    }
}
