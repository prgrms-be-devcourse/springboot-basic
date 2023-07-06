package com.example.voucher.ui;

import java.util.Scanner;

public class Input {
    private Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public String readInput() {
        return scanner.nextLine();
    }

    public double readVoucherInput() {
        return Double.parseDouble(scanner.nextLine());
    }
}

