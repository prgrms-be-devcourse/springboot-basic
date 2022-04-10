package com.example.voucher_manager.io;

import java.util.Scanner;

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
