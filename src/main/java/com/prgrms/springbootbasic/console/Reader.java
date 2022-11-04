package com.prgrms.springbootbasic.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Reader {

    private final Scanner scanner;

    public Reader() {
        scanner = new Scanner(System.in);
    }

    public String read() {
        return scanner.nextLine();
    }
}
