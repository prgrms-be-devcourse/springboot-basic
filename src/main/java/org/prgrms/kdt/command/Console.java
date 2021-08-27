package org.prgrms.kdt.command;

import java.util.Scanner;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class Console implements Input, Output {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);
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
            logger.warn(input);
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
        logger.warn(input);
        System.out.println("invalid command: " + input);
    }


}
