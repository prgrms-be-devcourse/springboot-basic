package com.devcourse.voucherapp.exception.voucher;

import static java.text.MessageFormat.format;

public class NotFoundVoucherException extends RuntimeException {

    private static final String NOT_FOUND_VOUCHER_MESSAGE = "입력하신 ID에 해당하는 할인권이 없습니다. 올바른 ID를 입력해주세요.";

    public NotFoundVoucherException(String id) {
        super(format("{0} | 현재 입력 : {1}", NOT_FOUND_VOUCHER_MESSAGE, id));
    }
}
