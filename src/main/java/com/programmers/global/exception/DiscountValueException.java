package com.programmers.global.exception;

public class DiscountValueException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 올바른 할인 값이 아닙니다.";

    public DiscountValueException() {
        super(MESSAGE);
    }
}
