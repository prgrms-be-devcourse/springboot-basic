package com.programmers.global.exception;

public class VoucherCommandException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 올바른 커맨드 값을 입력해주세요. ";

    public VoucherCommandException() {
        super(MESSAGE);
    }
}
