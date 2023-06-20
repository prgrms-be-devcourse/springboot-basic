package org.prgms.vouchermanagement.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console {

    private final Scanner scanner = new Scanner(System.in);

    public void printCommandMenu() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 'exit' to exit the program.");
        System.out.println("Type 'create' to create a new voucher.");
        System.out.println("Type 'list' to list all vouchers.");
        System.out.print("Input: ");
    }

    public String getCommand() {
        return scanner.nextLine();
    }
}
