package org.prgrms.java.common;

public enum MessageGuide {
    COMMAND_OPTION(
            "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers."
    ),
    VOUCHER_TYPE(
            "=== Select Voucher Type ===\n" +
            "1. Fixed Discount Voucher\n" +
            "2. Percentage Discount Voucher"
    ),
    VOUCHER_DISCOUNT_AMOUNT(
            "=== Enter The Amount ===\n" +
            "If you choose 'Fixed' : 1000\n" +
            "If you choose 'Percentage' : 10% -> 10"
    );

    private final String message;

    MessageGuide(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
