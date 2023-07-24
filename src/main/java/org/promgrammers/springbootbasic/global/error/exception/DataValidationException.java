package org.promgrammers.springbootbasic.global.error.exception;

public class DataValidationException extends RuntimeException {

    private ErrorCode errorCode;

    public DataValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
