package org.prgrms.part1;

import org.prgrms.part1.io.Input;
import org.prgrms.part1.io.Output;

import java.text.MessageFormat;
import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String select() {
        return scanner.nextLine();
    }

    @Override
    public String inputQuestion(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(String message) {
        System.out.println(MessageFormat.format("[Error] {0}", message));
    }
}
