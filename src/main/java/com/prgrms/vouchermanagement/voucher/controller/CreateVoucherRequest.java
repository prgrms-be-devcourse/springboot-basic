package com.prgrms.vouchermanagement.voucher.controller;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public class CreateVoucherRequest {
    private long amount;
    private VoucherType voucherType;

    public CreateVoucherRequest() {
    }

    public CreateVoucherRequest(long amount, VoucherType voucherType) {
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public long getAmount() {
        return amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
