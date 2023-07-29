package com.programmers.springbootbasic.common.util;

public final class NumberParser {
    static final String INVALID_NUMBER = "잘못된 수입니다. 현재 입력 값: ";

    private NumberParser() {
    }

    public static int parseToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_NUMBER + input);
        }
    }
}
