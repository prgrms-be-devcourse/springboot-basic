package com.programmers.springbootbasic.domain.voucher;

import java.util.Arrays;

public enum VoucherType {
    PERCENT("정률 할인"),
    FIX("정액 할인");

    private static final String INVALID_VOUCHER_TYPE = "잘못된 바우처 유형. 현재 입력 값: ";

    private final String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public static VoucherType from(String type) {
        return Arrays.stream(values())
                .filter(o -> type.equals(o.voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_VOUCHER_TYPE + type));
    }

    public String getVoucherType() {
        return voucherType;
    }
    
}
