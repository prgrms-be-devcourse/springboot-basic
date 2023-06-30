package com.devcourse.voucher.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String symbol;

    VoucherType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static boolean isIncorrectType(String input) {
        return Arrays.stream(VoucherType.values())
                .noneMatch(type -> isSame(type, input));
    }

    public static boolean isFixType(String input) {
        return StringUtils.equals(FIXED.symbol, input.toLowerCase());
    }

    private static boolean isSame(VoucherType type, String input) {
        return StringUtils.equals(type.symbol, input.toLowerCase());
    }
}
