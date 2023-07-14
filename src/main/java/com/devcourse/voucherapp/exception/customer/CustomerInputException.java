package com.devcourse.voucherapp.exception.customer;

import static java.text.MessageFormat.format;

public class CustomerInputException extends RuntimeException {

    private static final String INVALID_CUSTOMER_INPUT_MESSAGE = "입력하신 닉네임이 조건(공백이 없는 소문자 알파벳과 숫자 조합)에 맞지 않습니다. 다시 입력해주세요.";

    public CustomerInputException(String nickname) {
        super(format("{0} | 현재 입력 : {1}", INVALID_CUSTOMER_INPUT_MESSAGE, nickname));
    }
}
