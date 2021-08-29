package org.prgrms.kdt.exception;

public class VoucherNotFoundException extends RuntimeException{
    public VoucherNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
