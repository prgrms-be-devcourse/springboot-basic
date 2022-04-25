package com.prgms.management.common.exception;

public class DeleteFailException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "삭제에 실패했습니다.";

    public DeleteFailException() {
        this(DEFAULT_MESSAGE);
    }

    public DeleteFailException(String message) {
        super(message);
    }
}
