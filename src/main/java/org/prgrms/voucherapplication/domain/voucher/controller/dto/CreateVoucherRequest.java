package org.prgrms.voucherapplication.domain.voucher.controller.dto;

import org.prgrms.voucherapplication.domain.voucher.entity.VoucherType;

import java.util.UUID;

public class CreateVoucherRequest {
    private final UUID voucherId;
    private final int discount;
    private final String voucherType;

    public CreateVoucherRequest(int discount, String voucherType) {
        this.voucherId = UUID.randomUUID();
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
        return VoucherType.of(this.voucherType);
    }
}
