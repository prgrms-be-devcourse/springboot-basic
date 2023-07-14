package org.weekly.weekly.ui.reader;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.exception.InputValidator;

import java.util.Scanner;

@Component
@ConditionalOnProperty(value = "command.read", havingValue = "scanner")
public class ScannerWrap implements CommandReader {
    private final Scanner scanner;

    public ScannerWrap() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        String userInput = scanner.nextLine().trim();
        checkException(userInput);
        return userInput;
    }

    private void checkException(String userInput) {
        InputValidator.isEmpty(userInput);
    }
}
