package org.prgms.voucherProgram.exception;

public class DuplicateEmailException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 중복된 이메일이 존재합니다.";

    public DuplicateEmailException() {
        super(MESSAGE);
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
