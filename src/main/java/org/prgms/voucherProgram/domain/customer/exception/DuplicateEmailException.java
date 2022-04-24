package org.prgms.voucherProgram.domain.customer.exception;

public class DuplicateEmailException extends IllegalArgumentException {
    private static final String MESSAGE = "[ERROR] 이미 해당 이메일로 저장된 고객이 있습니다.";

    public DuplicateEmailException() {
        super(MESSAGE);
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
