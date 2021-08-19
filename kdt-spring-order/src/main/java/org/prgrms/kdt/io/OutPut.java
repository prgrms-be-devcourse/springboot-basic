package org.prgrms.kdt.io;

public class OutPut {

    public void printLine(String s) {
        System.out.println(s);
    }

    public void printCreateMenu() {
        System.out.println("=== Create Voucher ==");
        System.out.println("Enter the type of voucher");
        System.out.println("Enter the amount of discount");
        System.out.println("FixedAmountVoucher: 0, PercentAmountVoucher: 1");
        System.out.println("Please separate the type and amount with a space");
        System.out.println("Example: 0 10");
        System.out.println("type: FixedAmountVoucher, amount: 10");
        System.out.println("=====================");
    }

    public void printInitMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type List to list all vouchers");
    }
}
