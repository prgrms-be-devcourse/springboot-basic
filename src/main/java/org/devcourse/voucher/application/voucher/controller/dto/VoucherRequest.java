package org.devcourse.voucher.application.voucher.controller.dto;

import org.devcourse.voucher.application.voucher.model.VoucherType;

public class VoucherRequest {

    private VoucherType voucherType;

    private long discount;

    public VoucherRequest() {
    }

    public VoucherRequest(VoucherType voucherType, long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }
}
