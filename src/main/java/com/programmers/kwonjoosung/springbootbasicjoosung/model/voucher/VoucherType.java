package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import java.util.Objects;
import java.util.stream.Stream;

public enum VoucherType {

    FIXED("fixed"),
    PERCENT("percent");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType of(String inputVoucherType) {
        return Stream.of(VoucherType.values())
                .filter(value -> Objects.equals(value.type, inputVoucherType.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 할인종류가 아닙니다."));
    }
}
