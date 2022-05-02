package org.prgms.voucherProgram.global.error.exception;

public class EntityNotFoundException extends IllegalArgumentException {
    public static final String MESSAGE = "해당 Entity를 찾지 못했습니다.";

    public EntityNotFoundException() {
        super(MESSAGE);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
