package org.prgms.voucherProgram.exception;

public class AlreadyAssignException extends IllegalArgumentException {
    private static final String MESSAGE = "[ERROR] 해당 바우처는 이미 할당되었습니다.";

    public AlreadyAssignException() {
        super(MESSAGE);
    }

    public AlreadyAssignException(String message) {
        super(message);
    }
}
