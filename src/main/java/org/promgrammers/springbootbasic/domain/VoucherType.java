package org.promgrammers.springbootbasic.domain;

import java.util.Arrays;

public enum VoucherType {

    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType of(String voucherType) {
        return Arrays.stream(values())
                .filter(voucher -> voucher.type.equals(voucherType.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 Voucher 타입입니다."));
    }
}
