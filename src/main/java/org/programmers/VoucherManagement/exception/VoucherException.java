package org.programmers.VoucherManagement.exception;
public class VoucherException extends RuntimeException{

    private ExceptionMessage exceptionMessage;
    public VoucherException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
