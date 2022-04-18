package com.prgrms.management.config;

public enum GuideType {
    COMMAND("=== Voucher Program ===\nType exit to exit the program.\n"+
            "Type create to create a new voucher.\nType list to list all vouchers.\n" +
            "Type blacklist to list all black list customer.\nEnter Your Command"),
    VOUCHERTYPE("=== Choose Voucher ===\nType fixed to create a new FixedVoucher.\n" +
             "Type percent to create a new percentVoucher.\nEnter Your Voucher Type"),
    DISCOUNT("Enter the appropriate number for the discount policy"),
    VOUCHERID("Enter the Voucher ID"),
    CUSTOMERID("Enter the Customer ID"),
    CUSTOMERNAME("Enter the Customer Name"),
    CUSTOMEREMAIL("Enter the Customer Email"),
    CUSTOMERTYPE("Enter the Customer Type");

    private final String MESSAGE;

    GuideType(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }
}
