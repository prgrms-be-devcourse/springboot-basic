package com.programmers.voucher.io;

public class Output {
    public Output() {
    }

    public void printDescription() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type exit to exit the program.");
    }

    public void printTermination() {
        System.out.println("Exit application");
    }

    public void printStartOrder() {
        System.out.println("Type order");
    }
}
