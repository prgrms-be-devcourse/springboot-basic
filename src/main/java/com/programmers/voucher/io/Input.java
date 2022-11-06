package com.programmers.voucher.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {
    Scanner scanner = new Scanner(System.in);

    public String input() {
        return scanner.nextLine();
    }
}
