package org.prgrms.dev.io;

import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void init() {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }

    @Override
    public void voucherType() {
        System.out.println("=== select Voucher Type ===\n" +
                "fixed amount (f) \n" +
                "percent discount (p) ");
    }

    @Override
    public void success() {
        System.out.println("Voucher create success");
    }

    @Override
    public void inputError() {
        System.out.println("Invalid input.");
    }
}
