package org.prgrms.springbootbasic.exception;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;

public class FieldNotBlankException extends RuntimeException{
    private final ErrorCode errorCode;

    public FieldNotBlankException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
