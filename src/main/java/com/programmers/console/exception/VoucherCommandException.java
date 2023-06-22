package com.programmers.console.exception;

public class VoucherCommandException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 올바른 값을 입력해주세요. ";

    public VoucherCommandException() {
        super(MESSAGE);
    }
}
