package org.prgrms.kdt.command.io;

import java.util.Scanner;

public class Input {
    public static Scanner SCANNER = new Scanner(System.in);

    private Input() {
    }

    public static String input() {
        return SCANNER.nextLine();
    }
}
