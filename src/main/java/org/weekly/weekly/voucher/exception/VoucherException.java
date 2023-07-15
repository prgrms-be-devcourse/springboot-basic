package org.weekly.weekly.voucher.exception;

import org.weekly.weekly.util.ExceptionCode;

public class VoucherException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public VoucherException(ExceptionCode exceptionMsg) {
        super(exceptionMsg.getMessage());
        this.exceptionCode = exceptionMsg;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
