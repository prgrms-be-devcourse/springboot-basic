package org.prgms.kdt.application.voucher.exception;

public class VoucherDuplicateKeyException extends RuntimeException{
    public VoucherDuplicateKeyException() {
    }

    public VoucherDuplicateKeyException(String message) {
        super(message);
    }

    public VoucherDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public VoucherDuplicateKeyException(Throwable cause) {
        super(cause);
    }
}
