package com.programmers.VoucherManagementApplication.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {

    private final Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine();
    }
}
