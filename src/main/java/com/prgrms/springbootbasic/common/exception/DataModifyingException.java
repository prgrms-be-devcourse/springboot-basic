package com.prgrms.springbootbasic.common.exception;

public class DataModifyingException extends RuntimeException {
    private final ErrorCode errorCode;

    public DataModifyingException(String message) {
        super(message);
        this.errorCode = CommonErrorCode.DATA_MODIFYING_ERROR;
    }

    public DataModifyingException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
