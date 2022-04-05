package org.prgms.voucheradmin.global.error;

public enum ErrorMessage {
    WRONG_INPUT_EXCEPTION("wrong input");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
