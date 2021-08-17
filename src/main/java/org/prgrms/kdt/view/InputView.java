package org.prgrms.kdt.view;

import java.util.Scanner;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public static void initMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program. [exit]");
        System.out.println("Type create to create a new voucher. [create]");
        System.out.println("Type list to list all vouchers. [all]");
    }

    public static String input() {
        return scanner.nextLine();
    }

    public static void explainCreateVoucher() {
        System.out.println("=== Explain To Create Voucher ===");
        System.out.println("Check Your Type and Value");
        System.out.println("Fixed 1000");
        System.out.println("or");
        System.out.println("Percent 10");
    }

    public static void closeScanner() {
        scanner.close();
    }
}
