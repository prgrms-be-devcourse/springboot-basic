package com.prgrms.management.command.domain;

import com.prgrms.management.voucher.domain.Voucher;

public enum GuideMessageType {
    COMMAND("=== Voucher Program ===\nType exit to exit the program.\n"+
            "Type create to create a new voucher.\nType list to list all vouchers.\n" +
            "Type blacklist to list all black list customer.\nEnter Your Command"),
    VOUCHER("=== Choose Voucher ===\nType fixed to create a new FixedVoucher.\n" +
             "Type percent to create a new percentVoucher.\nEnter Your Voucher Type"),
    DISCOUNT("Enter the appropriate number for the discount policy");

    private final String GUIDE;
    GuideMessageType(String guide) {
        GUIDE = guide;
    }

    public String getGUIDE() {
        return GUIDE;
    }
}
