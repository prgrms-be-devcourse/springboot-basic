package com.devcourse.voucherapp.exception;

import static java.text.MessageFormat.format;

public class ExistedCustomerException extends RuntimeException {

    private static final String EXISTED_CUSTOMER_MESSAGE = "이미 존재하는 닉네임입니다.";

    public ExistedCustomerException(String nickname) {
        super(format("{0} | 입력 : {1}", EXISTED_CUSTOMER_MESSAGE, nickname));
    }
}
