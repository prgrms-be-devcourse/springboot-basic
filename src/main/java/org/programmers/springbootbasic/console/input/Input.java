package org.programmers.springbootbasic.console.input;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {

    private final Scanner scanner = new Scanner(System.in);

    public String readLine() {
        return scanner.nextLine();
    }
}
