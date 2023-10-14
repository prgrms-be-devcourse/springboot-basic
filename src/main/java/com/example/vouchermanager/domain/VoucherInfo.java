package com.example.vouchermanager.domain;

import com.example.vouchermanager.console.VoucherType;
import lombok.Getter;

@Getter
public class VoucherInfo {
    @Getter
    private long discount;
    private VoucherType voucherType;

    public VoucherInfo(VoucherType voucherType, long discount) {
        this.discount = discount;
        this.voucherType = voucherType;
    }
}
