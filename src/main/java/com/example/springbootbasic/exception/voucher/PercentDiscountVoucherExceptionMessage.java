package com.example.springbootbasic.exception.voucher;

public enum PercentDiscountVoucherExceptionMessage {

    PERCENT_DISCOUNT_RANGE_EXCEPTION("할인율의 가능한 범위는 {0}% ~ {1}% 입니다.");

    private final String message;

    PercentDiscountVoucherExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
