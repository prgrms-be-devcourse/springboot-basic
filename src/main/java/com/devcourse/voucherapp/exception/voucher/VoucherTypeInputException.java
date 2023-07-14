package com.devcourse.voucherapp.exception.voucher;

import static java.text.MessageFormat.format;

public class VoucherTypeInputException extends RuntimeException {

    private static final String NOT_EXIST_VOUCHER_TYPE_MESSAGE = "존재하지 않는 할인권 방식입니다. 다시 선택해주세요.";

    public VoucherTypeInputException(String voucherTypeNumber) {
        super(format("{0} | 현재 입력 : {1}", NOT_EXIST_VOUCHER_TYPE_MESSAGE, voucherTypeNumber));
    }
}
