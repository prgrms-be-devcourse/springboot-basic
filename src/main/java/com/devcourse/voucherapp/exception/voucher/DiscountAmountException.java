package com.devcourse.voucherapp.exception.voucher;

import static java.text.MessageFormat.format;

public class DiscountAmountException extends RuntimeException {

    public DiscountAmountException(String condition, String discountAmount) {
        super(format("입력하신 수치가 해당 할인권 방식의 조건({0})에 맞지 않습니다. 다시 입력해주세요. | 현재 입력 : {1}", condition, discountAmount));
    }
}
