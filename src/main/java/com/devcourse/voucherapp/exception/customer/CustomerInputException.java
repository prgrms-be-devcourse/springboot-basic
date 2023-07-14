package com.devcourse.voucherapp.exception.customer;

import static java.text.MessageFormat.format;

public class CustomerInputException extends RuntimeException {

    private static final String INVALID_CUSTOMER_INPUT_MESSAGE = "닉네임에는 공백 없이 소문자 알파벳과 숫자만 입력 가능합니다.";

    public CustomerInputException(String nickname) {
        super(format("{0} | 입력 : {1}", INVALID_CUSTOMER_INPUT_MESSAGE, nickname));
    }
}
