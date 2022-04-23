package org.prgrms.kdt.io;

import java.util.Scanner;

public class InputConsole implements Input{

    private final Scanner scanner;

    public InputConsole() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String inputString() {
        return scanner.nextLine();
    }

    @Override
    public String inputStringWithPrintMessage(String printMessage) {
        System.out.print(printMessage);
        return scanner.nextLine();
    }

}
