package org.prgrms.kdtspringdemo.view;

import java.io.BufferedReader;

public class OutputConsole {
    BufferedReader br;

    public OutputConsole(BufferedReader br) {
        this.br = br;
    }

    public void start() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type list to list all vouchers.");
    }
}
