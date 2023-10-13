package com.example.vouchermanager.domain;

import com.example.vouchermanager.console.VoucherType;

public class VoucherInfo {
    private long discount;
    private VoucherType voucherType;

    public VoucherInfo(VoucherType voucherType, long discount) {
        this.discount = discount;
        this.voucherType = voucherType;
    }
}
