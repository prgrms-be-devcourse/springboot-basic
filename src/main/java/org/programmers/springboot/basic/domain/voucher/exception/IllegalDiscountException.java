package org.programmers.springboot.basic.domain.voucher.exception;

public class IllegalDiscountException extends RuntimeException {

    public IllegalDiscountException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
