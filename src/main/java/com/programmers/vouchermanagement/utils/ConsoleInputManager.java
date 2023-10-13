package com.programmers.vouchermanagement.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInputManager {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsoleOutputManager consoleOutputManager;

    public ConsoleInputManager(ConsoleOutputManager consoleOutputManager) {
        this.consoleOutputManager = consoleOutputManager;
    }

    public String inputString() {
        return scanner.nextLine();
    }

    public Long inputDiscount() {

        String input = scanner.nextLine();

        while (!input.matches("^[0-9]+$")) {

            consoleOutputManager.printWrongInputLong();

            input = scanner.nextLine();
        }

        return Long.parseLong(input);
    }
}
