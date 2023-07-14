package com.programmers.springbootbasic.common.util;

import java.util.regex.Pattern;

public final class PatternUtils {
    private static final Pattern DATE = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final Pattern DATETIME = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
    private static final Pattern EMAIL = Pattern.compile("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");

    private PatternUtils() {
    }

    public static boolean isDate(String input) {
        return DATE.matcher(input).matches();
    }

    public static boolean isDateTime(String input) {
        return DATETIME.matcher(input).matches();
    }

    public static boolean isEmail(String input) {
        return EMAIL.matcher(input).matches();
    }
}
