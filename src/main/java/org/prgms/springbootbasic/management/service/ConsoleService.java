package org.prgms.springbootbasic.management.service;

import java.util.Scanner;

public class ConsoleService {
    private final Scanner sc = new Scanner(System.in);

    public void run() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
    }
}
