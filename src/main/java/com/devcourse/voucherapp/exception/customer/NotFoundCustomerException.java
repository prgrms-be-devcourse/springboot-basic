package com.devcourse.voucherapp.exception.customer;

import static java.text.MessageFormat.format;

public class NotFoundCustomerException extends RuntimeException {

    private static final String NOT_FOUND_CUSTOMER_MESSAGE = "입력하신 닉네임에 해당하는 고객이 없습니다. 올바른 닉네임을 입력해주세요.";

    public NotFoundCustomerException(String nickname) {
        super(format("{0} | 현재 입력 : {1}", NOT_FOUND_CUSTOMER_MESSAGE, nickname));
    }
}
