package com.programmers.springweekly.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Validator {

    private static final Pattern numberPattern = Pattern.compile("\\d+");
    private static final Pattern englishPattern = Pattern.compile("^[a-zA-Z]*$");
    private static final Pattern emailPattern = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
    private static final int PERCENT_MAX = 100;
    private static final int PERCENT_MIN = 0;

    public static void fixedAmountValidate(String fixedAmount) {
        validateNumber(fixedAmount);
    }

    public static void percentValidate(String percent) {
        validateNumber(percent);
        validateRange(percent);
    }

    public static String[] inputParse(String input) {
        return input.split(",");
    }

    public static void validateName(String input) {
        Matcher match = englishPattern.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Input : " + input + ", 입력하신 것은 영어가 아닙니다.");
        }
    }

    public static void validateEmail(String input) {
        Matcher match = emailPattern.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Input : " + input + "입력하신 것은 이메일이 아닙니다.");
        }
    }

    private static void validateNumber(String input) {
        Matcher match = numberPattern.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Input : " + input + ", 입력하신 것은 숫자가 아닙니다.");
        }
    }

    private static void validateRange(String inputPercent) {
        int percent = Integer.parseInt(inputPercent);

        if (percent > PERCENT_MAX || percent < PERCENT_MIN) {
            throw new IllegalArgumentException("Input : " + inputPercent + ", 입력하신 숫자는 범위를 벗어납니다.");
        }
    }
}
