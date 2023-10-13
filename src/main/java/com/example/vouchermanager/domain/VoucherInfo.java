package com.example.vouchermanager.domain;

import com.example.vouchermanager.console.VoucherType;
import lombok.Getter;

@Getter
public class VoucherInfo {
    @Getter
    private long amount;
    private VoucherType voucherType;

    public VoucherInfo(VoucherType voucherType, long amount) {
        this.amount = amount;
        this.voucherType = voucherType;
    }
}
