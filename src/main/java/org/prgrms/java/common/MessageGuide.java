package org.prgrms.java.common;

public enum MessageGuide {
    COMMAND_OPTION(
            "=== Voucher Program ===\n" +
            "Type " + MessageGuide.ANSI_BOLD + MessageGuide.ANSI_RED + "exit" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + MessageGuide.ANSI_RED + "exit" + MessageGuide.ANSI_RESET + " the program.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "create" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "create" + MessageGuide.ANSI_RESET + " a new voucher.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "list" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "list" + MessageGuide.ANSI_RESET + " all vouchers.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "blacklist" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "list" + MessageGuide.ANSI_RESET + " all blocked users.\n"
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

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_RED = "\u001B[31m";

    private final String message;

    MessageGuide(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
