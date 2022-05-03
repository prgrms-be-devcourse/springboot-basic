package org.programmers.kdt.weekly.voucher.controller;

import org.programmers.kdt.weekly.voucher.model.VoucherType;

public class CreateVoucherRequest {

    private final VoucherType voucherType;
    private final long value;

    public CreateVoucherRequest(VoucherType voucherType, long value) {
        this.voucherType = voucherType;
        this.value = value;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getValue() {
        return value;
    }
}
