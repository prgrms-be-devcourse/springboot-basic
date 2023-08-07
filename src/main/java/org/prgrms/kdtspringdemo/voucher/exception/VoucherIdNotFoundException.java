package org.prgrms.kdtspringdemo.voucher.exception;

public class VoucherIdNotFoundException extends RuntimeException {
    public VoucherIdNotFoundException(VoucherExceptionMessage message) {
        super(message.getMessage());
    }
}
