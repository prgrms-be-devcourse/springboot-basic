package com.programmers.voucher.enums;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    FIXED("fixed"), PERCENT("percent");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType getVoucherType(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(VoucherType -> Objects.equals(VoucherType.type, type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 바우처입니다."));
    }
}
