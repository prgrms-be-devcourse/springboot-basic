package org.prgrms.kdt.voucher.exception;

public class VoucherNotInsertedException extends RuntimeException{
    public VoucherNotInsertedException(String errorMessage) {
        super(errorMessage);
    }
}
