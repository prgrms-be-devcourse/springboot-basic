package com.programmers.vouchermanagement.common;

public enum ConsoleMessage {
    COMMAND_LIST_MESSAGE("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """),

    INVALID_COMMAND_MESSAGE("[System] Invalid command."),
    EXIT_MESSAGE("[System] Program exited.");
    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
