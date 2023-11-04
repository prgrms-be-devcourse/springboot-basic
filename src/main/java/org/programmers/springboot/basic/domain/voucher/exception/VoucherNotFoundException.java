package org.programmers.springboot.basic.domain.voucher.exception;

public class VoucherNotFoundException extends RuntimeException {

    public VoucherNotFoundException() {
        super("No matching vouchers found!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
