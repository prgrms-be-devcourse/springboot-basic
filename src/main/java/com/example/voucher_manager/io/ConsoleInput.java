package com.example.voucher_manager.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input{
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String displayString) {
        System.out.print(displayString);
        return scanner.nextLine();
    }

    @Override
    public Long parseLong(String input) {
        return Long.parseLong(input);
    }
}
