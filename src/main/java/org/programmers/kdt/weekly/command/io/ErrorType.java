package org.programmers.kdt.weekly.command.io;

public enum ErrorType {
    CUSTOMER_EMPTY("There are no saved customers."),
    COMMAND("This command does not exist."),
    INVALID("Invalid input."),
    VOUCHER_EMPTY("There are no saved vouchers."), VOUCHER_WALLET_EMPTY("There are no saved Voucher Wallet."),
    DUPLICATE_EMAIL("This email already exists.");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public static String getMessage(ErrorType errorType) {
        return errorType.message;
    }

}