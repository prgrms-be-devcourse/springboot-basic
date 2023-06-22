package org.programers.vouchermanagement.exception;

public class NoSuchVoucherException extends RuntimeException {

    public NoSuchVoucherException(String message) {
        super(message);
    }

    public NoSuchVoucherException() {
        this("존재하지 않는 바우처입니다.");
    }
}
