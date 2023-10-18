package com.prgrms.vouchermanager.message;

import lombok.Getter;

@Getter
public enum ConsoleMessage {
    SELECT_FUNCTION("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type blacklist to list blacklist customers."""),
    FINISH_PROGRAM("Exit the program."),
    GET_VOUCHER_TYPE("""
            Type fixed if you want a fixed discount voucher.
            Type percent if you want a percent discount voucher."""),
    GET_DISCOUNT_AMOUNT("Type the discount amount."),
    GET_DISCOUNT_PERCENT("Type the discount percent."),
    COMPLETE_CREATE_VOUCHER("A voucher has been created."),

    EMPTY_LIST_EXCEPTION("There is nothing registered."),
    FILE_IO_EXCEPTION("Fail to file IO."),
    NOT_CORRECT_COMMAND("The command is incorrect."),
    NOT_CORRECT_FORM("The input format is correctly."),
    NOT_CORRECT_SCOPE("Percent range is incorrect.");


    private final String message;
    ConsoleMessage(String message) {
        this.message = message;
    }

}
