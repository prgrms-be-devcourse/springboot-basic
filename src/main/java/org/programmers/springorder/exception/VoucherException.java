package org.programmers.springorder.exception;

public class VoucherException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String message;

    public VoucherException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
