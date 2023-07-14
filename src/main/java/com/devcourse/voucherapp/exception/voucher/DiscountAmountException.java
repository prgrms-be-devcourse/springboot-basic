package com.devcourse.voucherapp.exception.voucher;

import static java.text.MessageFormat.format;

public class DiscountAmountException extends RuntimeException {

    private static final String INVALID_DISCOUNT_AMOUNT_MESSAGE = "입력하신 수치가 조건에 맞지 않습니다.";

    public DiscountAmountException(String discountAmount) {
        super(format("{0} | 입력 : {1}", INVALID_DISCOUNT_AMOUNT_MESSAGE, discountAmount));
    }
}
