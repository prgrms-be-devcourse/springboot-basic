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
    NOT_CORRECT_FORM("This is not an correct input format."),
    NOT_CORRECT_SCOPE("The discount percent must be a number from 0 to 100."),

    GET_DISCOUNT_AMOUNT("Type the discount amount."),
    GET_DISCOUNT_PERCENT("Type the discount percent."),
    COMPLETE_CREATE_VOUCHER("A voucher has been created.");

    private final String message;
    ConsoleMessage(String message) {
        this.message = message;
    }

}
