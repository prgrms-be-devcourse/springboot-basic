package org.prgrms.springbasic.utils.enumm.message;

public enum CustomerConsole {

    CUSTOMER_COMMAND_LIST("""
                === Voucher Program ===
                Type normal to register normal customer.
                Type black to register black customer.
               """
    ),
    INPUT_CUSTOMER_NAME("""
            === Voucher Program ===
            Type name of customer.
            """
    );

    private final String message;

    CustomerConsole(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
