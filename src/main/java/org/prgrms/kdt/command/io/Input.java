package org.prgrms.kdt.command.io;

import java.util.Scanner;

public class Input {
    static Scanner scanner = new Scanner(System.in);

    public static String input() {
        return scanner.nextLine();
    }
}
