package com.prgrms.springbasic.io;

public enum ConsoleMessage {

    START_PROGRAM("""
             === Start Program ===
            """),

    CHOOSE_MENU_TYPE("Please choose one of the menu displayed below and enter it."),
    START_CHOOSING_ACTION("""
             === Choose Action ===
            """),
    EXIT_PROGRAM("=== The program ends ===" + System.lineSeparator()),
    CHOOSE_COMMAND_TYPE("Please choose one of the selection displayed below and enter it."),
    CREATE_VOUCHER("=== Create Voucher ===" + System.lineSeparator()),
    GET_DISCOUNT_TYPE("Choose voucher type displayed below and enter it."),
    GET_FIXED_DISCOUNT_VALUE("Type discount value : "),
    GET_PERCENT_DISCOUNT_VALUE("Type discount percent : "),
    FIND_ALL_VOUCHERS("=== Show all vouchers ==="),
    UPDATE_VOUCHER("=== Update voucher ===" + System.lineSeparator()),
    DELETE_ALL_VOUCHER("=== Delete all vouchers" + System.lineSeparator()),
    NO_VOUCHER_EXIST("<warn> Couldn't find voucher information."),
    NO_CUSTOMER_EXIST("<warn> Couldn't find customer information."),
    CREATE_CUSTOMER("=== Create Customer ===" + System.lineSeparator()),
    GET_NAME("Type name"),
    GET_ID("Type id"),
    GET_EMAIL("Type email");

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
