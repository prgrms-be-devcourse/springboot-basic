package com.devcourse.springbootbasic.engine.exception;

public class InvalidDataException extends RuntimeException {
    public static final String INVALID_MENU = "올바른 메뉴를 선택해주세요.";
    public static final String INVALID_VOUCHER_TYPE = "올바른 바우처 메뉴를 선택해주세요.";
    public static final String INVALID_VOUCHER_INFO = "올바른 바우처 정보를 입력해주세요.";
    public static final String INVALID_DISCOUNT_VALUE = "부적절한 값입니다. 0.0 ~ 100.0 사이의 값을 입력해주세요.";

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String errorMessage) {
        super(errorMessage);
    }
}
