package org.prgms.voucherProgram.console.exception;

public class WrongCommandException extends IllegalArgumentException {
    private static final String MESSAGE = "[ERROR] 올바른 메뉴 입력이 아닙니다.";

    public WrongCommandException() {
        super(MESSAGE);
    }

    public WrongCommandException(String message) {
        super(message);
    }
}
