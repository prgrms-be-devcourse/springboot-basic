package org.prgrms.springbootbasic.exception;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;

//Voucher 생성 시 value 값이 정상 범위를 벗어났을 때 발생하는 exception
public class VoucherValueRangeException extends RuntimeException{
    private final ErrorCode errorCode;

    public VoucherValueRangeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
