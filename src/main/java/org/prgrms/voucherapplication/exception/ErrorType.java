package org.prgrms.voucherapplication.exception;

public enum ErrorType {
    INVALID_MENU("Invalid menu"),
    INVALID_VOUCHER_TYPE("Invalid voucher type"),
    NO_SUCH_VOUCHER("No such voucher.");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
