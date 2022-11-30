package com.prgrms.springbootbasic.common.exception;

public class InvalidVoucherTypeException extends RuntimeException {
    private final ErrorCode errorCode;

//    public InvalidVoucherTypeException(String message) {
//        super(message);
//    }

    public InvalidVoucherTypeException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
