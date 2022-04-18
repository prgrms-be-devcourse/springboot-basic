package org.prgrms.springbasic.utils.enumm.message;

public enum ConsoleMessage {

    INIT_MESSAGE("""
                    === Voucher Program ===
                ---------------------------------
                Type register to register customer.
                Type customers to list all customers.
                ---------------------------------
                Type create to create a new voucher.
                Type vouchers to list all vouchers.
                ---------------------------------
                Type exit to exit the program.
                """
    );

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
