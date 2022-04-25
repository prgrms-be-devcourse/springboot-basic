package com.prgms.management.common.exception;

public class SaveFailException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "저장에 실패했습니다.";

    public SaveFailException() {
        this(DEFAULT_MESSAGE);
    }

    public SaveFailException(String message) {
        super(message);
    }
}
