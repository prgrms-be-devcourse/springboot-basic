package com.programmers.springbootbasic.common.util;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

public final class Validator {
    private static final String PERCENT = "정률 할인";
    private static final String FIX = "정액 할인";
    private static final String ID_IS_NULL = "빈 값이나 공백을 아이디로 사용할 수 없습니다. 현재 값: ";
    private static final String NULL_OR_BLANK = "빈 값이나 공백을 사용할 수 없습니다. 현재 값: ";
    private static final String INVALID_VOUCHER_TYPE = "잘못된 바우처 유형입니다. 현재 입력 값: ";
    private static final String DATETIME_IS_NULL = "빈 값이나 공백을 날짜/시간으로 지정할 수 없습니다. 현재 값: ";
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final Pattern DATETIME_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}$\n");

    private Validator() {
    }

    public static void checkNullUuid(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException(ID_IS_NULL + uuid);
        }
    }

    public static void checkInvalidType(String input) {
        if (input.equals(PERCENT) || input.equals(FIX)) {
            return;
        }
        throw new IllegalArgumentException(INVALID_VOUCHER_TYPE + input);
    }

    public static void checkNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(NULL_OR_BLANK + input);
        }
    }

    public static void checkInvalidDateTime(LocalDateTime input) {
        if (input == null) {
            throw new IllegalArgumentException(DATETIME_IS_NULL + input);
        }
    }

    public static boolean isDate(String input) {
        return DATE_PATTERN.matcher(input).matches();
    }

    public static boolean isDateTime(String input) {
        return DATETIME_PATTERN.matcher(input).matches();
    }

}
