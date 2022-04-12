package org.prgrms.weeklymission.utils;

public enum ConsoleMessage {
    INIT_MESSAGE("""
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                Type register to register black customer.
                Type blacks to list all black customers.
                """),
    VOUCHER_TYPE_LIST("""
                === Voucher Program ===
                Type fixed to make fixed voucher.
                Type percent to make percent voucher.
                """),
    CREATE_FIXED_VOUCHER("""
            === Voucher Program ===
            Type discount amount.
            """),
    CREATE_PERCENT_VOUCHER("""
            === Voucher Program ===
            Type discount percent.
            """),
    REGISTER_BLACK("""
            Type black customer's name.
            """);

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
