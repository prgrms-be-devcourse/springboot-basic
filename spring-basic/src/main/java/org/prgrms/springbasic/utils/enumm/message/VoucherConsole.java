package org.prgrms.springbasic.utils.enumm.message;

public enum VoucherConsole {

    VOUCHER_COMMAND_LIST("""        
                === Voucher Program ===
                Type fixed to make fixed voucher.
                Type percent to make percent voucher.
                """
    ),
    TYPE_DISCOUNT_INFO("""
            === Voucher Program ===
            Type discount information.
            """
    );

    private final String message;

    VoucherConsole(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
