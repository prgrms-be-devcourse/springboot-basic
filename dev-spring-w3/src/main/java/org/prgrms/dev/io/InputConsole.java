package org.prgrms.dev.io;

import java.util.Scanner;

public class InputConsole implements Input{
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String inputValue(String input) {
        System.out.print(input);
        return scanner.nextLine();
    }

    @Override
    public String inputCommandType(String input) {
        System.out.print(input);
        return scanner.nextLine();
    }

    @Override
    public String inputVoucherType(String input) {
        System.out.print(input);
        return scanner.nextLine();
    }
}
