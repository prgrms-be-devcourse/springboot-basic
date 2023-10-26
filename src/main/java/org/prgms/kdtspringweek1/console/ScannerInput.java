package org.prgms.kdtspringweek1.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerInput implements ConsoleInput {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getInput() {
        return scanner.nextLine();
    }
}
