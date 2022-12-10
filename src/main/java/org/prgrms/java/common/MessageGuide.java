package org.prgrms.java.common;

import java.text.MessageFormat;

public enum MessageGuide {
    COMMAND_OPTION(
            MessageFormat.format(
                    """
                    === Voucher Program ===
                    Type {0}{1}exit {2}to {0}{1}exit {2}the program.
                    Type {0}voucher {2}to {0}manipulate {2}all vouchers.
                    Type {0}customer {2}to {0}manipulate {2}all customers.
                    Type {0}wallet {2}to {0}manipulate {2}all wallet commands.
                    """
                    , MessageGuide.ANSI_BOLD, MessageGuide.ANSI_RED, MessageGuide.ANSI_RESET
            )
    ),
    VOUCHER_COMMAND_OPTION(
            MessageFormat.format(
                    """
                        === Voucher Service ===
                        Type {0}create {1}to {0}create {1}a new voucher.
                        Type {0}find {1}to {0}find {1}a voucher.
                        Type {0}list {1}to {0}list {1}all vouchers.
                        Type {0}delete {1}to {0}delete {1}a voucher.
                        Type {0}delete_all {1}to {0}delete {1}all vouchers.
                        Type {0}any unlisted command {1}to {0}go back {1}to main menu.
                        """
                    , MessageGuide.ANSI_BOLD, MessageGuide.ANSI_RESET
            )
    ),
    VOUCHER_FIND_COMMAND_OPTION(
            """
            === Select Search Option ===
            1. VOUCHER ID
            2. OWNER ID
            """
    ),
    REQUIRE_VOUCHER_TYPE(
            """
            === Select Voucher Type ===
            1. Fixed Discount Voucher (0~)
            2. Percentage Discount Voucher (1~100)
            """
    ),
    REQUIRE_VOUCHER_DISCOUNT_AMOUNT("=== Enter The Amount ==="),
    REQUIRE_VOUCHER_ID("=== Type Voucher ID ==="),
    CUSTOMER_COMMAND_OPTION(
            MessageFormat.format(
                    """
                    === Customer Service ===
                    Type {0}create {1}to {0}create {1}a new customer.
                    Type {0}find {1}to {0}find {1}a customer.
                    Type {0}list {1}to {0}list {1}all customers.
                    Type {0}blacklist {1}to {0}list {1}all black customers.
                    Type {0}delete {1}to {0}delete {1}a customer.
                    Type {0}delete_all {1}to {0}delete {1}all customers.
                    Type {0}any unlisted command {1}to {0}go back {1}to main menu.
                    """
                    , MessageGuide.ANSI_BOLD, MessageGuide.ANSI_RESET
            )
    ),
    CUSTOMER_FIND_COMMAND_OPTION(
            """
            === Select Search Option ===
            1. ID
            2. NAME
            3. EMAIL
            """
    ),
    REQUIRE_CUSTOMER_ID("=== Type Customer ID ==="),
    REQUIRE_CUSTOMER_NAME("=== Type Customer Name ==="),
    REQUIRE_CUSTOMER_EMAIL("=== Type Customer Email ==="),
    WALLET_COMMAND_OPTION(
            MessageFormat.format(
                    """
                    === Wallet Service ===
                    Type {0}allocate {1}to {0}allocate {1}a voucher to a customer.
                    Type {0}show {1}to {0}show {1}all vouchers of a customer.
                    Type {0}find {1}to {0}find {1}a customer with a voucher.
                    Type {0}delete {1}to {0}remove {1}a vouchers from a customer's wallet.
                    Type {0}delete_all {1}to {0}remove {1}all vouchers from a customer's wallet.
                    Type {0}any unlisted command {1}to {0}allocate {1}to main menu.
                    """
                    , MessageGuide.ANSI_BOLD, MessageGuide.ANSI_RESET
            )
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
