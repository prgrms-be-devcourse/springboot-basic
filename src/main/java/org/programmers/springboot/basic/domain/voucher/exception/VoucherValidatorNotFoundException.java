package org.programmers.springboot.basic.domain.voucher.exception;

public class VoucherValidatorNotFoundException extends RuntimeException {

    public VoucherValidatorNotFoundException() {
        super("No matching validator found!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
