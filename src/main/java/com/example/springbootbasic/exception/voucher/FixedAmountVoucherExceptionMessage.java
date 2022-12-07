package com.example.springbootbasic.exception.voucher;

public enum FixedAmountVoucherExceptionMessage {

    FIXED_AMOUNT_DISCOUNT_RANGE_EXCEPTION("고정 할인의 가능한 금액 범위는 {0}원 ~ {1}원 입니다.");

    private final String message;

    FixedAmountVoucherExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
