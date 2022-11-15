package org.prgrms.springbootbasic.util;


import java.util.Scanner;

public class CommandLineInput {
    private static final Scanner scanner = new Scanner(System.in);
    public static String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}
