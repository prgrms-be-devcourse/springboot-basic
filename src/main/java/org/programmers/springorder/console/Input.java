package org.programmers.springorder.console;

import org.programmers.springorder.utils.Validation;

import java.util.Scanner;

public class Input {

    private final Scanner scanner = new Scanner(System.in);

    public String getInput() {
        return scanner.nextLine();
    }
}
