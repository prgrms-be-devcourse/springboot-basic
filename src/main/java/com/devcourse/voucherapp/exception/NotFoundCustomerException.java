package com.devcourse.voucherapp.exception;

import static java.text.MessageFormat.format;

public class NotFoundCustomerException extends RuntimeException {

    private static final String NOT_FOUND_CUSTOMER_MESSAGE = "입력하신 ID에 해당하는 고객이 없습니다.";

    public NotFoundCustomerException(String id) {
        super(format("{0} | 입력 : {1}", NOT_FOUND_CUSTOMER_MESSAGE, id));
    }
}
