package com.zerozae.voucher.validator;

import com.zerozae.voucher.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InputValidator {

    private static final String ALPHABET_AND_KOREAN_PATTERN = "[a-zA-Z\uAC00-\uD7A3]+";
    private static final String NUMBER_PATTERN = "\\d+";
    private static final Logger logger = LoggerFactory.getLogger(InputValidator.class);

    public static Long validateInputDiscount(String input) {
        if (input != null && input.matches(NUMBER_PATTERN)) {
            return Long.valueOf(input);
        } else {
            String message = "입력값은 숫자여야 합니다.";
            logger.warn("Error Message = {}", message);
            throw ErrorMessage.error(message);
        }
    }

    public static String validateInputString(String input) {
        if (input != null && input.matches(ALPHABET_AND_KOREAN_PATTERN)) {
            return input;
        } else {
            String message = "입력값은 문자열이어야 합니다.";
            logger.warn("Error Message = {}", message);
            throw ErrorMessage.error(message);
        }
    }
}
