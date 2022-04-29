package org.prgrms.springbootbasic.exception;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;

public class InvalidInputFormatException extends RuntimeException{
    private final ErrorCode errorCode;
    public InvalidInputFormatException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
