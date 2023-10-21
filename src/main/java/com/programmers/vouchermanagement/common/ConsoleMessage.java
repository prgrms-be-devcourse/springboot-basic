package com.programmers.vouchermanagement.common;

public enum ConsoleMessage {
    COMMAND_LIST_MESSAGE("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type blacklist to list customer blacklist.
            """),
    EXIT_MESSAGE("[System] Program exited."),

    // create voucher messages
    CHOICE_VOUCHER_TYPE_MESSAGE("[System] Please choice voucher type"),
    FIXED_AMOUNT_VOUCHER_GUIDE_MESSAGE("""
            [System] Fixed amount voucher will be created.
            [System] Enter "voucher name" and "fixed value" to discount.
            """),
    PERCENT_DISCOUNT_VOUCHER_GUIDE_MESSAGE("""
            [System] Percent discount voucher will be created.
            [System] Enter "voucher name" and "percent value(%)" to discount.
            """),
    INPUT_VOUCHER_NAME_MESSAGE("Enter voucher name: "),
    INPUT_VOUCHER_AMOUNT_MESSAGE("Enter voucher amount: "),
    INPUT_VOUCHER_PERCENT_MESSAGE("Enter voucher percent: "),
    VOUCHER_CREATED_MESSAGE("[System] Voucher created.");

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
