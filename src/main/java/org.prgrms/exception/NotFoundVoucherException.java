package org.prgrms.exception;

public class NotFoundVoucherException extends RuntimeException{

    private ErrorCode errorCode;

    public NotFoundVoucherException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public NotFoundVoucherException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
