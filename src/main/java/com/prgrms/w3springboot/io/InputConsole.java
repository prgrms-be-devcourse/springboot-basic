package com.prgrms.w3springboot.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class InputConsole implements Input {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String input() {
        return scanner.next();
    }
}
