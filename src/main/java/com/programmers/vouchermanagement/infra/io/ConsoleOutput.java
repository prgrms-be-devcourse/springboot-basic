package com.programmers.vouchermanagement.infra.io;

public class ConsoleOutput {
    public static void printHelp() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type [exit] to exit the program.");
        System.out.println("Type [create] to create a new voucher.");
        System.out.println("Type [list] to list all vouchers.");
        System.out.println("=== Customer Program ===");
        System.out.println("Type [blacklist] to list all blacklisted customers.");
    }

    public static void println(String message) {
        System.out.println(message);
    }
}
