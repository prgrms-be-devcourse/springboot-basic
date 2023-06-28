package org.programmers.VoucherManagement.exception;

public class VoucherException extends RuntimeException {

    private VoucherExceptionMessage exceptionMessage;

    public VoucherException(VoucherExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
