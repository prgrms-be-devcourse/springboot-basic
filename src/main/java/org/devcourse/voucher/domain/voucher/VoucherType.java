package org.devcourse.voucher.domain.voucher;

public enum VoucherType {
    FIX("fix"),
    PERCENT("percent");

    private final String typeName;

    VoucherType(String typeName) {
        this.typeName = typeName;
    }
}
