package com.programmers.springbootbasic.common.util;

public final class NumberParser {
    static final Long ZERO = 0L;
    static final String INVALID_NUMBER = "잘못된 수입니다. 현재 입력 값: ";

    private NumberParser() {
    }

    public static Long parseToMinimumPriceCondition(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return ZERO;
        }
    }

    public static int parseToAmountOrPercent(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_NUMBER + input);
        }
    }
}
