package com.programmers.springweekly.domain.voucher;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String type;
    private static final Map<String, VoucherType> VOUCHER_MAP =
            Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(VoucherType::getVoucherTypeString, Function.identity())));

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType findVoucherMenu(String type) {
        if (VOUCHER_MAP.containsKey(type)) {
            return VOUCHER_MAP.get(type);
        }

        throw new IllegalArgumentException("Input: " + type + ", The type you are looking for is not found.");
    }

    public String getVoucherTypeString() {
        return type;
    }
}