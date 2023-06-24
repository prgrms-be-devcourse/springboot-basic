package org.weekly.weekly.ui.reader;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.exception.ReadException;

import java.util.Scanner;

@Component
@Primary
public class ScannerWrap implements CommandReader{
    private final Scanner scanner;

    public ScannerWrap() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        String userInput = this.scanner.nextLine().trim();
        checkException(userInput);
        return userInput.toLowerCase();
    }

    private void checkException(String userInput) {
        ReadException.isEmpty(userInput);
    }
}
