package org.prgrms.springbasic.domain.voucher;

public enum VoucherType {

    FIXED("fixed"), PERCENT("percent");

    private final String value;

    VoucherType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    };
}
