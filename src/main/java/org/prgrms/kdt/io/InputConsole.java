package org.prgrms.kdt.io;

import java.util.Scanner;

public class InputConsole implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input() {
        return scanner.nextLine();
    }
}
