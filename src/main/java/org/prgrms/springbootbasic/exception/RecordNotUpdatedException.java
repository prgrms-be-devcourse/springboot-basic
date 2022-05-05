package org.prgrms.springbootbasic.exception;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;

//Update, Insert, Delete 가 제대로 이루어지지 않았을 시 발생하는 exception
public class RecordNotUpdatedException extends RuntimeException{
    private final ErrorCode errorCode;

    public RecordNotUpdatedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
