package com.prgrms.springbasic.io;

public enum ConsoleMessage {
    START_VOUCHER_PROGRAM("""
             === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type black_list to list all black lists.
            """),
    EXIT_PROGRAM("=== The program ends ==="),
    CHOOSE_MENU_TYPE("Please choose one of the menu displayed below and enter it."),
    CREATE_VOUCHER("=== Create Voucher ===" + System.lineSeparator()),
    GET_DISCOUNT_TYPE("Choose voucher type displayed below and enter it."),
    GET_DISCOUNT_VALUE("Type discount value : "),
    FIND_ALL_VOUCHERS("=== Show all vouchers ===" + System.lineSeparator()),
    NO_VOUCHER_EXIST("<warn> Couldn't find voucher information."),
    NO_CUSTOMER_EXIST("<warn> Couldn't find customer information.")
    ;

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
