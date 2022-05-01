package org.prgrms.voucherapp.exception;

public class UnknownVoucherTypeException extends RuntimeException {
    public UnknownVoucherTypeException(String message) {
        super(message);
    }

    public UnknownVoucherTypeException() {
        super("알 수 없는 바우처 타입입니다.");
    }
}
