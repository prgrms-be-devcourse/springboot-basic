package com.prgrms.management.config;

public enum GuideType {
    COMMAND("=== Voucher Program ===\n" +
            "Type CREATEVOUCHER to create a new voucher.\n" +
            "Type LISTVOUCHER to list all vouchers.\n" +
            "Type BLACKLIST to list all black list customer.\n" +
            "Type CREATECUSTOMER to create a new voucher.\n" +
            "Type UPDATECUSTOMER to update a voucher.\n" +
            "Type DELETECUSTOMER to delete a voucher.\n" +
            "Type DELETEALLCUSTOMER to delete all voucher.\n" +
            "Type LISTCUSTOMER to list all list customer.\n" +
            "Type FINDCUSTOMERBYID to find a voucher by Id.\n" +
            "Type FINDCUSTOMERBYEMAIL to find a voucher by email.\n" +
            "Type ASSIGNVOUCHER to assign customer's voucher.\n" +
            "Type UNASSIGNVOUCHER to unassign customer's voucher.\n" +
            "Type LISTVOUCHERWITHTYPE to list all customerId with voucherType.\n" +
            "Type exit to exit the program.\nEnter Your Command"),
    VOUCHERTYPE("=== Choose Voucher ===\n" +
            "Type fixed to create a new FixedVoucher.\n" +
            "Type percent to create a new percentVoucher.\n" +
            "Enter Your Voucher Type"),
    DISCOUNT("Enter the appropriate number for the discount policy"),
    VOUCHERID("Enter the Voucher ID"),
    CUSTOMERID("Enter the Customer ID"),
    CUSTOMERNAME("Enter the Customer Name"),
    CUSTOMEREMAIL("Enter the Customer Email"),
    CUSTOMERTYPE("Enter the Customer Type"),
    EMAIL("Enter the Customer Email");

    private final String MESSAGE;

    GuideType(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }
}
