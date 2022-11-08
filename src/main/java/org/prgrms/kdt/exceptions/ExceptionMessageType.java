package org.prgrms.kdt.exceptions;

public enum ExceptionMessageType {
    AMOUNT_UNDER_ZERO("숫자가 0보다 커야 합니다. 다시 작성해주세요."),
    AMOUNT_OVER_100("퍼센트는 100 이하여야 합니다.");

    private final String message;

    ExceptionMessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
