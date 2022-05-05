package org.prgrms.springbootbasic.exception;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;

//Table에 특정 record가 없을 시 발생하는 exception
public class RecordNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public RecordNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
