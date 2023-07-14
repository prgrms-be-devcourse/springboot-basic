package org.weekly.weekly.customer.exception;

import org.weekly.weekly.util.ExceptionCode;

public class CustomerException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public CustomerException(ExceptionCode exceptionMsg) {
        super(exceptionMsg.getMessage());
        this.exceptionCode = exceptionMsg;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
