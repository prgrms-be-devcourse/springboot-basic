package com.devcourse.voucherapp.exception.customer;

import static java.text.MessageFormat.format;

public class CustomerTypeInputException extends RuntimeException {

    private static final String NOT_EXIST_VOUCHER_TYPE_MESSAGE = "입력하신 고객 타입은 없는 타입입니다.";

    public CustomerTypeInputException(String customerTypeNumber) {
        super(format("{0} | 입력 : {1}", NOT_EXIST_VOUCHER_TYPE_MESSAGE, customerTypeNumber));
    }
}
