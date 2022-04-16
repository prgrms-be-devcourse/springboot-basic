package org.prgms.voucherProgram.exception;

public class WrongFileException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 올바른 파일이 아닙니다.";

    public WrongFileException() {
        super(MESSAGE);
    }

    public WrongFileException(String message) {
        super(message);
    }
}
