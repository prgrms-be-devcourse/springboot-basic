package org.prgrms.kdt.io;

import java.util.Scanner;

public class InputConsole {

    private final Scanner scanner;

    public InputConsole() {
        this.scanner = new Scanner(System.in);
    }

    public String inputString() {
        return scanner.nextLine();
    }

    public String inputStringWithPrintMessage(String printMessage) {
        System.out.print(printMessage);
        return scanner.nextLine();
    }

}
