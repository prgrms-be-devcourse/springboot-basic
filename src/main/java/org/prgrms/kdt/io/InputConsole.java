package org.prgrms.kdt.io;

import java.util.Scanner;

public class InputConsole implements Input{

    private final Scanner scanner;

    public InputConsole() {
        this.scanner = new Scanner(System.in);
    }

    public String inputFunction() {
        return scanner.nextLine();
    }

    public String inputVoucherType() {
        return scanner.nextLine();
    }

    public String inputAmount() {
        System.out.print("Type amount : ");
        return scanner.nextLine();
    }
}
