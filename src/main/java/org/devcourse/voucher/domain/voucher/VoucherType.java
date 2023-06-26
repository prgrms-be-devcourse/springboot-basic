package org.devcourse.voucher.domain.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIX("fix"),
    PERCENT("percent");

    private final String typeName;

    VoucherType(String typeName) {
        this.typeName = typeName;
    }

    public static VoucherType find(String typeName) {
        return Arrays.stream(values())
                .filter(type -> type.typeName.equals(typeName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("사용 불가능한 바우처 타입입니다"));
    }
}
