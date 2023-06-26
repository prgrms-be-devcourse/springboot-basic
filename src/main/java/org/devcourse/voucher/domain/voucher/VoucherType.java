package org.devcourse.voucher.domain.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIX("fix"),
    PERCENT("percent"),
    NO_TYPE("no type");

    private final String typeName;

    VoucherType(String typeName) {
        this.typeName = typeName;
    }

    public static VoucherType find(String typeName) {
        return Arrays.stream(values())
                .filter(type -> type.typeName.equals(typeName))
                .findFirst()
                .orElse(NO_TYPE);
    }
}
