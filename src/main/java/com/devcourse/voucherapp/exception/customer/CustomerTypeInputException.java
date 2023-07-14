package com.devcourse.voucherapp.exception.customer;

import static java.text.MessageFormat.format;

public class CustomerTypeInputException extends RuntimeException {

    private static final String NOT_EXIST_VOUCHER_TYPE_MESSAGE = "존재하지 않는 고객 타입입니다. 다시 선택해주세요.";

    public CustomerTypeInputException(String customerTypeNumber) {
        super(format("{0} | 현재 입력 : {1}", NOT_EXIST_VOUCHER_TYPE_MESSAGE, customerTypeNumber));
    }
}
