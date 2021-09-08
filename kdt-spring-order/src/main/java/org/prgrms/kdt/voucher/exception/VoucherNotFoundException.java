package org.prgrms.kdt.voucher.exception;

public class VoucherNotFoundException extends RuntimeException{
    public VoucherNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
