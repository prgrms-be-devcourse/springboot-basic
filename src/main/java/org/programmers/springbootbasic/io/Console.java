package org.programmers.springbootbasic.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    public String input(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public void printMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type blacklist to list all blacklists.");
    }

    public void printSuccessMessage() {
        System.out.println("Voucher has been created");
    }
}
