package com.example.vouchermanager.message;

public enum ConsoleMessage {
    SELECT_FUNCTION("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """),
    FINISH_PROGRAM("프로그램을 종료합니다."),
    SELECT_VOUCHER_TYPE("""
            Type fixed if you want a fixed voucher.
            Type percent if you want a percent voucher.
            """),
    NOT_CORRECT_COMMAND("This is not a valid command.");

    ConsoleMessage(String message) {
    }
}
