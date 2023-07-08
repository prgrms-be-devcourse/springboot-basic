package com.prgmrs.voucher.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReader {
    private final Scanner sc = new Scanner(System.in);

    public String read() {
        return sc.next();
    }
}
