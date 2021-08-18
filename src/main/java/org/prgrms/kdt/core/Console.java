package org.prgrms.kdt.core;

import org.prgrms.kdt.core.*;

import java.util.Scanner;

public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public String input(String input) {
        System.out.println(input);
        return scanner.nextLine();
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void inputError(String input) {
        System.out.println("command not found: " + input);
    }

}
