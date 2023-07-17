package org.prgrms.kdtspringdemo.voucher.exception;

public class VoucherSaveFailedException extends RuntimeException {
    public VoucherSaveFailedException(VoucherExceptionMessage message) {
        super(message.getMessage());
    }
}
