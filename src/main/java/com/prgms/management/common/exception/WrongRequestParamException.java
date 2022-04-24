package com.prgms.management.common.exception;

public class WrongRequestParamException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "잘못된 파라미터 값입니다.";
    
    public WrongRequestParamException() {
        this(DEFAULT_MESSAGE);
    }
    
    public WrongRequestParamException(String message) {
        super(message);
    }
}
