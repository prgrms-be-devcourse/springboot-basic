package com.zerozae.voucher.validator;

import com.zerozae.voucher.exception.ExceptionMessage;

import java.io.IOException;
import java.util.UUID;


public class InputValidator {

    private static final String KOREAN_ALPHABET_PATTERN = "^[a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_]*$";
    private static final String NUMBER_PATTERN = "-?\\d+";

    private InputValidator() {
    }

    public static Long validateInputNumber(String input) {
        if (input != null && input.matches(NUMBER_PATTERN)) {
            return Long.valueOf(input);
        } else {
            throw ExceptionMessage.error("입력값은 숫자여야 합니다.");
        }
    }

    public static String validateInputString(String input) {
        if (input != null && input.matches(KOREAN_ALPHABET_PATTERN)) {
            return input;
        } else {
            throw ExceptionMessage.error("입력값은 문자열이어야 합니다.");
        }
    }

    public static String validateInputUuid(String input){
        try {
            UUID uuid = UUID.fromString(input);
            return uuid.toString();
        } catch (IllegalArgumentException e) {
            throw ExceptionMessage.error("유효하지 않은 UUID 형식입니다.");
        }
    }
}
