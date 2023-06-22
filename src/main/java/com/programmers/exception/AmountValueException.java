package com.programmers.exception;

public class AmountValueException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 올바른 입력값이 아닙니다.";

    public AmountValueException() {
        super(MESSAGE);
    }
}
