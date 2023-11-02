package com.programmers.vouchermanagement.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class ConsoleInputManager {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleInputManager.class);

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

            logger.warn("Invalid input discount. Input : " + input);

            consoleOutputManager.printWrongInputLong();
            input = scanner.nextLine();
        }

        return Long.parseLong(input);
    }

    public UUID inputUUID() {

        String input = scanner.nextLine();

        while (!input.matches("[0-9A-Za-z]{8}-[0-9A-Za-z]{4}-[0-9A-Za-z]{4}-[0-9A-Za-z]{4}-[0-9A-Za-z]{12}")) {

            logger.warn("Invalid input uuid. Input : " + input);

            consoleOutputManager.printWrongInputUuid();
            input = scanner.nextLine();
        }

        return UUID.fromString(input);
    }
}
