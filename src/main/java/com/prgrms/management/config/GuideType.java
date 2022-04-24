package com.prgrms.management.config;

public enum GuideType {
    COMMAND("=== Voucher Program ===\n" +
            "1. Type CREATEVOUCHER to create a new voucher.\n" +
            "2. Type LISTVOUCHER to list all vouchers.\n" +
            "3. Type BLACKLIST to list all black list customer.\n" +
            "4. Type CREATECUSTOMER to create a new voucher.\n" +
            "5. Type UPDATECUSTOMER to update a voucher.\n" +
            "6. Type DELETECUSTOMER to delete a voucher.\n" +
            "7. Type DELETEALLCUSTOMER to delete all voucher.\n" +
            "8. Type LISTCUSTOMER to list all list customer.\n" +
            "9. Type FINDCUSTOMERBYID to find a voucher by Id.\n" +
            "10. Type FINDCUSTOMERBYEMAIL to find a voucher by email.\n" +
            "11. Type ASSIGNVOUCHER to assign customer's voucher.\n" +
            "12. Type DELETEVOUCHER to delete customer's voucher.\n" +
            "13. Type LISTVOUCHERWITHTYPE to list all customerId with voucherType.\n" +
            "0. Type exit to exit the program.\nEnter Your Command"),
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
