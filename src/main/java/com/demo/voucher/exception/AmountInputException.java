package com.demo.voucher.exception;

public class AmountInputException extends IllegalArgumentException {
    private static final String AMOUNT_INPUT_EXCEPTION_MESSAGE = "올바른 할인 금액을 입력하지 않았습니다.";

    public AmountInputException() {
        super(AMOUNT_INPUT_EXCEPTION_MESSAGE);
    }
}
