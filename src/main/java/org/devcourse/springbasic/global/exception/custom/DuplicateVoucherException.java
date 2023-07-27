package org.devcourse.springbasic.global.exception.custom;

public class DuplicateVoucherException extends IllegalArgumentException {
    public DuplicateVoucherException(String msg) {
        super(msg);
    }
}
