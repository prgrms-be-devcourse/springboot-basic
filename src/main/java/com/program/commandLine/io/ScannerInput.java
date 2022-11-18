package com.program.commandLine.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerInput implements Input {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public String input(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
