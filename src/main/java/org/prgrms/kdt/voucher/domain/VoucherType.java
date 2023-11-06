package org.prgrms.kdt.voucher.domain;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    VoucherType(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }
}
