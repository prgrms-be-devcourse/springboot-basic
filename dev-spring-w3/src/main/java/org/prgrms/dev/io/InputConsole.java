package org.prgrms.dev.io;

import java.util.Scanner;

public class InputConsole implements Input{
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
