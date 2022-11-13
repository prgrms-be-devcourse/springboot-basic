package com.programmers.commandline.global.util;

import java.util.regex.Pattern;

public class Verification {

    private Verification() {}

    public static void validateParseToNumber(String input) {
        Pattern numberPattern = Pattern.compile("^[0-9]*$");

        if (!numberPattern.matcher(input).matches()) {
            throw new IllegalArgumentException("잘못된 입력 값 입니다. 정상 입력은 숫자 입니다.");
        }
    }
}
