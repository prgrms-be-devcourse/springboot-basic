package com.programmers.kdtspringorder.voucher.controller;

import com.programmers.kdtspringorder.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Value;

public class VoucherCreateRequest {

    private VoucherType voucherType;
    private long value;

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
