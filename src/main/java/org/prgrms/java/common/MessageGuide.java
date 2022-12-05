package org.prgrms.java.common;

public enum MessageGuide {
    COMMAND_OPTION(
            "=== Voucher Program ===\n" +
            "Type " + MessageGuide.ANSI_BOLD + MessageGuide.ANSI_RED + "exit" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + MessageGuide.ANSI_RED + "exit" + MessageGuide.ANSI_RESET + " the program.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "voucher" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "manipulate" + MessageGuide.ANSI_RESET + " all vouchers.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "customer" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "manipulate" + MessageGuide.ANSI_RESET + " all customers.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "wallet" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "manipulate" + MessageGuide.ANSI_RESET + " all wallet commands."
    ),
    VOUCHER_COMMAND_OPTION(
            "=== Voucher Service ===\n" +
            "Type " + MessageGuide.ANSI_BOLD + "create" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "create" + MessageGuide.ANSI_RESET + " a new voucher.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "find" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "find" + MessageGuide.ANSI_RESET + " a voucher.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "list" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "list" + MessageGuide.ANSI_RESET + " all vouchers.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "delete" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "delete" + MessageGuide.ANSI_RESET + " a voucher.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "delete_all" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "delete" + MessageGuide.ANSI_RESET + " all vouchers.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "any unlisted command" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "go back" + MessageGuide.ANSI_RESET + " to main menu."
    ),
    VOUCHER_FIND_COMMAND_OPTION(
            "=== Select Search Option ===\n" +
            "1. VOUCHER ID\n" +
            "2. OWNER ID"
    ),
    REQUIRE_VOUCHER_TYPE(
            "=== Select Voucher Type ===\n" +
            "1. Fixed Discount Voucher (0~)\n" +
            "2. Percentage Discount Voucher (1~100)"
    ),
    REQUIRE_VOUCHER_DISCOUNT_AMOUNT("=== Enter The Amount ==="),
    REQUIRE_VOUCHER_ID("=== Type Voucher ID ==="),
    CUSTOMER_COMMAND_OPTION(
            "=== Customer Service ===\n" +
            "Type " + MessageGuide.ANSI_BOLD + "create" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "create" + MessageGuide.ANSI_RESET + " a new customer.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "find" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "find" + MessageGuide.ANSI_RESET + " a customer.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "list" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "list" + MessageGuide.ANSI_RESET + " all customers.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "blacklist" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "blacklist" + MessageGuide.ANSI_RESET + " all black customers.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "delete" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "delete" + MessageGuide.ANSI_RESET + " a customer.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "delete_all" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "delete" + MessageGuide.ANSI_RESET + " all customers.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "any unlisted command" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "go back" + MessageGuide.ANSI_RESET + " to main menu."
    ),
    CUSTOMER_FIND_COMMAND_OPTION(
            "=== Select Search Option ===\n" +
            "1. ID\n" +
            "2. NAME\n" +
            "3. EMAIL"
    ),
    REQUIRE_CUSTOMER_ID("=== Type Customer ID ==="),
    REQUIRE_CUSTOMER_NAME("=== Type Customer Name ==="),
    REQUIRE_CUSTOMER_EMAIL("=== Type Customer Email ==="),
    WALLET_COMMAND_OPTION(
            "=== Wallet Service ===\n" +
            "Type " + MessageGuide.ANSI_BOLD + "allocate" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "allocate" + MessageGuide.ANSI_RESET + " a voucher to a customer.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "show" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "show" + MessageGuide.ANSI_RESET + " the vouchers of a customer.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "find" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "find" + MessageGuide.ANSI_RESET + " a customer with a voucher.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "delete" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "remove" + MessageGuide.ANSI_RESET + " a voucher from a customer's wallet.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "delete_all" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "remove" + MessageGuide.ANSI_RESET + " all vouchers from a customer's wallet.\n" +
            "Type " + MessageGuide.ANSI_BOLD + "any unlisted command" + MessageGuide.ANSI_RESET + " to " + MessageGuide.ANSI_BOLD + "go back" + MessageGuide.ANSI_RESET + " to main menu."
    ),
    SUCCESS_MESSAGE("Successfully Applied.");

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
