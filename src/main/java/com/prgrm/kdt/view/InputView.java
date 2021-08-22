package com.prgrm.kdt.view;

import java.util.Scanner;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public static String input() {
        return scanner.nextLine();
    }

    public static void closeScanner() {
        scanner.close();
    }

    public static void initVoucherMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    public static void createVoucherMessage() {
        System.out.println("=== CREATE VOUCHER ===");
        System.out.println("Type Fixed to create FixedAmountVoucher.");
        System.out.println("Type Percent to create PercentAmountVoucher.");
    }

    public static void enterVoucherDiscountMessage() {
        System.out.println("할인율(Percent) or 할인금액(Fixed)을 입력해주세요.");
    }
}
