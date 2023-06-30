package com.prgrms.springbootbasic.enums;

public enum VoucherType {
    FIXED("Fixed"),
    PERCENT("Percent");
    private final String voucherType;


    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }
}
