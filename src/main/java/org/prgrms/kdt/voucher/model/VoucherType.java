package org.prgrms.kdt.voucher.model;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String voucherName;


    VoucherType(String voucherName) {
        this.voucherName = voucherName;
    }
}
