package com.programmers.springweekly.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomerValidator {

    private static final Pattern englishPattern = Pattern.compile("^[a-zA-Z]*$");
    private static final Pattern emailPattern = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");

    public static void validateInputCustomerInfo(String name, String email) {
        validateName(name);
        validateEmail(email);
    }

    private static void validateName(String input) {
        Matcher match = englishPattern.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Input : " + input + ", 입력하신 것은 영어가 아닙니다.");
        }
    }

    private static void validateEmail(String input) {
        Matcher match = emailPattern.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Input : " + input + "입력하신 것은 이메일이 아닙니다.");
        }
    }

}
