package org.programmers.springboot.basic.domain.voucher.exception;

public class DuplicateVoucherException extends RuntimeException {

    public DuplicateVoucherException() {
        super("Duplicate voucher already exists!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
