package com.programmers.springbootbasic.common.util;

import java.util.regex.Pattern;

public final class PatternUtils {
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final Pattern DATETIME_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");

    private PatternUtils() {
    }

    public static boolean isDate(String input) {
        return DATE_PATTERN.matcher(input).matches();
    }

    public static boolean isDateTime(String input) {
        return DATETIME_PATTERN.matcher(input).matches();
    }

}
