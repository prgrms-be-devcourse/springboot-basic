package com.devcourse.voucherapp.exception.voucher;

import static java.text.MessageFormat.format;

public class VoucherTypeInputException extends RuntimeException {

    private static final String NOT_EXIST_VOUCHER_TYPE_MESSAGE = "입력하신 할인권 방식은 없는 방식입니다.";

    public VoucherTypeInputException(String voucherTypeNumber) {
        super(format("{0} | 입력 : {1}", NOT_EXIST_VOUCHER_TYPE_MESSAGE, voucherTypeNumber));
    }
}
