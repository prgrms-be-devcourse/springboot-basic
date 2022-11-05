package org.prgrms.kdt.voucher;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }
}
