package com.example.vouchermanager.message;

public enum ConsoleMessage {
    SELECT_FUNCTION("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """);

    ConsoleMessage(String message) {
    }
}
