package com.zerozae.voucher.validator;

import com.zerozae.voucher.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.zerozae.voucher.common.message.MessageConverter.getMessage;

public class InputValidator {
    private static final Logger logger = LoggerFactory.getLogger(InputValidator.class);
    public static Long validateInputDiscount(String input) {
        if (input != null && input.matches("\\d+")) {
            return Long.valueOf(input);
        } else {
            String message = getMessage("INPUT_TYPE_INTEGER.MSG");
            logger.error("Error Message = {}", message);
            throw ExceptionHandler.err(message);
        }
    }
    public static String validateInputString(String input) {
        if (input != null && input.matches("[a-zA-Z\uAC00-\uD7A3]+")) {
            return input;
        } else {
            String message = getMessage("INPUT_TYPE_STRING.MSG");
            logger.error("Error Message = {}", message);
            throw ExceptionHandler.err(message);
        }
    }
}
