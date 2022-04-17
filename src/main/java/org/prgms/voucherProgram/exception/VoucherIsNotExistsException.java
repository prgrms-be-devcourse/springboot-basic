package org.prgms.voucherProgram.exception;

public class VoucherIsNotExistsException extends IllegalArgumentException {
    private static final String MESSAGE = "[ERROR] 해당 아이디로 저장된 바우처가 없습니다.";

    public VoucherIsNotExistsException() {
        super(MESSAGE);
    }

    public VoucherIsNotExistsException(String message) {
        super(message);
    }
}
