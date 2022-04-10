package org.prgrms.weeklymission.voucher.domain;

public enum VoucherType {
    FIXED("FIXED"), PERCENT("PERCENT");

    private final String value;
    VoucherType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    };
}
