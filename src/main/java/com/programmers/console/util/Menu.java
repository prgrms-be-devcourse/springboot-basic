package com.programmers.console.util;

public enum Menu {
    COMMAND_MESSAGE("""
            === Voucher Program ===
            Type **exit** to exit the program.
            Type **create** to create a new IVoucher.
            Type **list** to list all vouchers."""),
    CREATE_VOUCHER_TYPE_MESSAGE("""
            === Select a IVoucher type ===
            1 : FixedAmountVoucher  
            2 : PercentDiscountVoucher""");

    private final String message;

    Menu(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
