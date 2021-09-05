package com.programmers.kdtspringorder.voucher.controller;

import com.programmers.kdtspringorder.voucher.VoucherType;

import java.time.LocalDateTime;

public class VoucherLookupConditionRequest {

    private VoucherType voucherType;
    private LocalDateTime localDateTime;

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
