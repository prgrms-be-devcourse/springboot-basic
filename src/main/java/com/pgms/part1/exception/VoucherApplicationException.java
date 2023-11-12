package com.pgms.part1.exception;

public class VoucherApplicationException extends RuntimeException{

    private ErrorCode errorCode;

    public VoucherApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
