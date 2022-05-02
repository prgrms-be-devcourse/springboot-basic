package org.prgrms.springbootbasic.view;

public enum ViewText {
        
    SELECT_VOUCHER_TYPE("Select voucher type"),
    SELECT_AMOUNT("Select amount"),
    SELECT_PERCENT("Select percent"),
    SELECT_CUSTOMER_NAME("Select customer name"),
    SELECT_CUSTOMER_EMAIL("Select customer email"),
    SELECT_VOUCHER_ID("Select voucher id"),
    SELECT_CUSTOMER_ID("Select customer id"),


    VOUCHER_PROGRAM("=== Voucher Program ==="),
    VOUCHER_LIST("==Voucher List=="),
    CUSTOMER_S_VOUCHER_LIST("=== CUSTOMER'S VOUCHER LIST ==="),
    CUSTOMER_LIST("=== CUSTOMER LIST ==="),
    CUSTOMER_BLACK_LIST("=== CUSTOMER_BLACK_LIST ==="),
    FAIL("=== FAIL ==="),


    TYPE("Type "),


    TO_EXIT_THE_PROGRAM(" to exit the program."),
    TO_CREATE_A_NEW_VOUCHER(" to create a new voucher."),
    TO_LIST_ALL_VOUCHERS(" to list all vouchers."),
    TO_LIST_ALL_CUSTOMER_BLACK_LIST(" to list all customer black list."),
    TO_CREATE_A_NEW_CUSTOMER(" to create a new customer."),
    TO_LIST_ALL_CUSTOMERS(" to list all customers."),
    TO_ASSIGN_VOUCHER_TO_CUSTOMER(" to assign voucher to customer."),
    TO_LIST_CUSTOMER_S_VOUCHER(" to list customer's voucher."),
    TO_DELETE_CUSTOMER_S_VOUCHER(" to delete customer's voucher."),
    TO_LIST_CUSTOMERS_HAVING_SPECIFIC_VOUCHER_TYPE(
        " to list customers having specific voucher type."),


    VOUCHER_ID("voucherId= "),
    AMOUNT(", amount= "),
    PERCENT(", percent= "),
    CUSTOMER_LIST_PATTERN("UUID = {0}, name = {1}, email = {2}");

    private final String text;

    ViewText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
