package com.programmers.global.exception;

public class NotFoundException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 찾을 수 없습니다.";

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException() {
        super(MESSAGE);
    }
}
