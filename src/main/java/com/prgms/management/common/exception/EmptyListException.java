package com.prgms.management.common.exception;

public class EmptyListException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "저장된 데이터가 없습니다.";

    public EmptyListException() {
        this(DEFAULT_MESSAGE);
    }

    public EmptyListException(String message) {
        super(message);
    }
}
