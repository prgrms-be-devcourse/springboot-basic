package org.prgrms.voucherapplication.voucher.controller;

import org.prgrms.voucherapplication.voucher.entity.VoucherType;

import java.util.UUID;

public class CreateVoucherRequest {
    private final UUID voucherId;
    private final int discount;
    private final VoucherType voucherType;

    public CreateVoucherRequest(UUID voucherId, int discount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public int getDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
