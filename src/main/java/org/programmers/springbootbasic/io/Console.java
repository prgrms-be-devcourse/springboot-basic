package org.programmers.springbootbasic.io;

import java.util.Scanner;

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
        System.out.println("Type update to update a voucher.");
        System.out.println("Type delete to delete a voucher.");
    }

    public void printSuccessMessage() {
        System.out.println("Voucher has been created");
    }

    public void printSuccessUpdateMessage() {
        System.out.println("Voucher has been updated");
    }

    public void printSuccessDeleteMessage() {
        System.out.println("Voucher has been deleted");
    }
}
