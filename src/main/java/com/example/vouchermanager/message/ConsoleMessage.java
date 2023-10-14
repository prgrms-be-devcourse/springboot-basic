package com.example.vouchermanager.message;

import lombok.Getter;

@Getter
public enum ConsoleMessage {
    SELECT_FUNCTION("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """),
    FINISH_PROGRAM("Exit the program."),
    SELECT_VOUCHER_TYPE("""
            Type fixed and discount amount if you want a fixed voucher.
            Type percent and discount percent if you want a percent voucher.
            ex) fixed 1000
            ex) percent 30
            """),
    NOT_CORRECT_COMMAND("This is not a valid command."),
    GET_PRICE("Type the amount before discount."),
    COMPLETE_CREATE_VOUCHER("A voucher has been created.");

    private final String message;
    ConsoleMessage(String message) {
        this.message = message;
    }

}
