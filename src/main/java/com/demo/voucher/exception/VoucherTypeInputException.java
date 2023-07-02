package com.demo.voucher.exception;

public class VoucherTypeInputException extends IllegalArgumentException {
    private static final String VOUCHER_TYPE_INPUT_EXCEPTION_MESSAGE = "올바른 바우처 타입을 입력하지 않았습니다.";

    public VoucherTypeInputException() {
        super(VOUCHER_TYPE_INPUT_EXCEPTION_MESSAGE);
    }
}
