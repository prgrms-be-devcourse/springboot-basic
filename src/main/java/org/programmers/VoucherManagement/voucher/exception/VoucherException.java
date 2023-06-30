package org.programmers.VoucherManagement.voucher.exception;

public class VoucherException extends RuntimeException {

    private VoucherExceptionMessage exceptionMessage;

    public VoucherException(VoucherExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }

    public VoucherException(VoucherExceptionMessage exceptionMessage, Throwable cause) {
        super(exceptionMessage.getMessage(), cause);
    }
}
