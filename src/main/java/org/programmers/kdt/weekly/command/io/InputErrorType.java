package org.programmers.kdt.weekly.command.io;

public enum InputErrorType {
    CUSTOMER_EMPTY("There are no saved customers."),
    COMMAND("This command does not exist."),
    INVALID("Invalid input."),
    VOUCHER_EMPTY("There are no saved vouchers."), VOUCHER_WALLET_EMPTY("There are no saved Voucher Wallet."),
    DUPLICATE_EMAIL("This email already exists.");

    private final String message;

    InputErrorType(String message) {
        this.message = message;
    }

    public static String getMessage(InputErrorType inputErrorType) {
        return inputErrorType.message;
    }

}