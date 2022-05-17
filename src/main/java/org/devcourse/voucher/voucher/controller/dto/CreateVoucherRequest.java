package org.devcourse.voucher.voucher.controller.dto;

import org.devcourse.voucher.voucher.model.VoucherType;

public class CreateVoucherRequest {
    private final VoucherType voucherType;
    private final long price;

    public CreateVoucherRequest(VoucherType voucherType, long price) {
        this.voucherType = voucherType;
        this.price = price;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getPrice() {
        return price;
    }
}
