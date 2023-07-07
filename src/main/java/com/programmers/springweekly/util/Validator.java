package com.programmers.springweekly.util;

import com.programmers.springweekly.domain.voucher.VoucherType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Validator {

    private static final Pattern numberPattern = Pattern.compile("\\d+");
    private static final Pattern englishPattern = Pattern.compile("^[a-zA-Z]*$");
    private static final Pattern emailPattern = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
    private static final int PERCENT_MAX = 100;
    private static final int PERCENT_MIN = 0;

    public static void validateDiscountAmount(VoucherType voucherType, String discountAmount) {
        if (voucherType == VoucherType.FIXED) {
            numberValidate(discountAmount);
        }

        if (voucherType == VoucherType.PERCENT) {
            numberValidate(discountAmount);
            rangeValidate(discountAmount);
        }
    }

    public static String[] inputParse(String input) {
        return input.split(",");
    }

    public static void nameValidate(String input) {
        Matcher match = englishPattern.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Input : " + input + ", 입력하신 것은 영어가 아닙니다.");
        }
    }

    public static void emailValidate(String input) {
        Matcher match = emailPattern.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Input : " + input + "입력하신 것은 이메일이 아닙니다.");
        }
    }

    private static void numberValidate(String input) {
        Matcher match = numberPattern.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Input : " + input + ", 입력하신 것은 숫자가 아닙니다.");
        }
    }

    private static void rangeValidate(String inputPercent) {
        int percent = Integer.parseInt(inputPercent);

        if (percent > PERCENT_MAX || percent < PERCENT_MIN) {
            throw new IllegalArgumentException("Input : " + inputPercent + ", 입력하신 숫자는 범위를 벗어납니다.");
        }
    }
}
