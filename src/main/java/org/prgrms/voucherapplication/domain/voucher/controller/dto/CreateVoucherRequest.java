package org.prgrms.voucherapplication.domain.voucher.controller.dto;

import org.prgrms.voucherapplication.domain.voucher.entity.VoucherType;

public class CreateVoucherRequest {
    private int discount;
    private String voucherType;

    public int getDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return VoucherType.of(this.voucherType);
    }
}
