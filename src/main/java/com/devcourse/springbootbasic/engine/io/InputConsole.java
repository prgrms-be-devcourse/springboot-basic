package com.devcourse.springbootbasic.engine.io;

import java.util.Scanner;

public class InputConsole {

    private final Scanner scanner = new Scanner(System.in);

    public String inputMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("=======================");
        System.out.print("Menu Type : ");
        return scanner.nextLine();
    }

    public String inputVoucher() {
        return "";
    }
}
