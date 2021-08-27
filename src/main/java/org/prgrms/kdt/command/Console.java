package org.prgrms.kdt.command;

import java.util.Scanner;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;


@Component
public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input() {
        return scanner.nextLine().toLowerCase();
    }

    @Override
    public String input(String msg, Predicate<String> p) {
        System.out.println(msg);
        var input = input();
        while (!p.test(input)) {
            printError(input);
            input = input();
        }
        return input;
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(String input) {
        System.out.println("invalid command: " + input);
    }


}
