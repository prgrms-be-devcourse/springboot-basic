package com.zerozae.voucher.validator;

import com.zerozae.voucher.exception.ErrorMessage;


public class InputValidator {

    private static final String KOREAN_ALPHABET_PATTERN = "^[a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$";
    private static final String NUMBER_PATTERN = "-?\\d+";

    public static Long validateInputNumber(String input) {
        if (input != null && input.matches(NUMBER_PATTERN)) {
            return Long.valueOf(input);
        } else {
            throw ErrorMessage.error("입력값은 숫자여야 합니다.");
        }
    }

    public static String validateInputString(String input) {
        if (input != null && input.matches(KOREAN_ALPHABET_PATTERN)) {
            return input;
        } else {
            throw ErrorMessage.error("입력값은 문자열이어야 합니다.");
        }
    }
}