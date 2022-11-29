package org.prgrms.voucherapplication.global.exception;

public class BusinessException extends RuntimeException {
    private int status;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessege());
        this.status = exceptionCode.getStatus();
    }

    public int getStatus() {
        return status;
    }
}
