package org.programmers.springbootbasic.voucher.domain;

public class IllegalVoucherTypeException extends IllegalArgumentException {
    public IllegalVoucherTypeException(String s) {
        super(s);
    }
}