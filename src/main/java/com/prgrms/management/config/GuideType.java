package com.prgrms.management.config;

public enum GuideType {
    COMMAND("=== Voucher Program ===\n" +
            "1. create a new voucher.\n" +
            "2. list all vouchers.\n" +
            "3. list all black list customer.\n" +
            "4. to create a new voucher.\n" +
            "5. to update a voucher.\n" +
            "6. to delete a voucher.\n" +
            "7. to delete all voucher.\n" +
            "8. to list all list customer.\n" +
            "9. to find a voucher by Id.\n" +
            "10. to find a voucher by email.\n" +
            "11. to assign customer's voucher.\n" +
            "12. to delete customer's voucher.\n" +
            "13. to list all customerId with voucherType.\n" +
            "0. to exit the program.\n" +
            "Enter Your Command"),
    VOUCHER_TYPE("=== Choose Voucher ===\n" +
            "Type fixed to create a new FixedVoucher.\n" +
            "Type percent to create a new percentVoucher.\n" +
            "Enter Your Voucher Type"),
    DISCOUNT("Enter the appropriate number for the discount policy"),
    VOUCHER_ID("Enter the Voucher ID"),
    CUSTOMER_ID("Enter the Customer ID"),
    CUSTOMER_NAME("Enter the Customer Name"),
    CUSTOMER_EMAIL("Enter the Customer Email"),
    CUSTOMER_TYPE("Enter the Customer Type"),
    EMAIL("Enter the Customer Email");

    private final String MESSAGE;

    GuideType(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }
}
