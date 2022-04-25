package com.prgms.management.common.exception;

public class UpdateFailException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "정보 수정에 실패했습니다.";

    public UpdateFailException() {
        this(DEFAULT_MESSAGE);
    }

    public UpdateFailException(String message) {
        super(message);
    }
}
