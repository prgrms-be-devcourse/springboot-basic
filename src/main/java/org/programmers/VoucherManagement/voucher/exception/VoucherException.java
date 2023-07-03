package org.programmers.VoucherManagement.voucher.exception;

public class VoucherException extends RuntimeException {

    private final VoucherExceptionMessage exceptionMessage;

    public VoucherException(VoucherExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }

    public VoucherException(VoucherExceptionMessage exceptionMessage, Throwable cause) {
        super(exceptionMessage.getMessage(), cause);
        this.exceptionMessage = exceptionMessage;
    }
}
