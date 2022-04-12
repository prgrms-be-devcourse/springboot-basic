package org.prgrms.kdtspringdemo.console;

public class Output {
    public String initMessage() {
        String message = "=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n" +
                "write one of three word : (exit, create, list)";

        return message;
    }

    public String createListMessage() {
        String message = "=== Create Voucher ===\n" +
                "FixedAmountVoucher = 1\n" +
                "PercentDiscountVoucher = 2\n" +
                "Write number or voucher name : ";

        return message;
    }

    public String showAllMessage() {
        String message = "=== Show all Voucher ===\n" +
                "These are all vouchers list";

        return message;
    }
}
