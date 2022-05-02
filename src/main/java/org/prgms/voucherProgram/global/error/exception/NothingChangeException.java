package org.prgms.voucherProgram.global.error.exception;

public class NothingChangeException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 해당 요청이 정상적으로 처리되지 않았습니다.";

    public NothingChangeException() {
        super(MESSAGE);
    }

    public NothingChangeException(String message) {
        super(message);
    }
}
