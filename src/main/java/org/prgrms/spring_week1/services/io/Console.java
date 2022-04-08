package org.prgrms.spring_week1.services.io;

import java.util.Scanner;

public class Console implements Input, Output{

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void input() {
        scanner.nextLine();

    }

    @Override
    public void output(String str) {
        System.out.println(str);
    }

    @Override
    public void menu() {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }
}
