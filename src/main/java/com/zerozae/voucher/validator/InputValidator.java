package com.zerozae.voucher.validator;

import com.zerozae.voucher.exception.ErrorMessage;


public class InputValidator {

    private static final String KOREAN_PATTERN = "^[ㄱ-ㅎ가-힣]*$";
    private static final String ALPHABET_PATTERN =  "^[a-zA-Z]*$";
    private static final String NUMBER_PATTERN = "\\d+";

    public static Long validateInputDiscount(String input) {
        if (input != null && input.matches(NUMBER_PATTERN)) {
            return Long.valueOf(input);
        } else {
            String message = "입력값은 숫자여야 합니다.";
            throw ErrorMessage.error(message);
        }
    }

    public static String validateInputString(String input) {
        if (input != null && (input.matches(ALPHABET_PATTERN) || input.matches(KOREAN_PATTERN))) {
            return input;
        } else {
            String message = "입력값은 문자열이어야 합니다.";
            throw ErrorMessage.error(message);
        }
    }
}
