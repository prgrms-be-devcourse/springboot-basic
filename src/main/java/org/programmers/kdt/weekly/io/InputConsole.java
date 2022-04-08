package org.programmers.kdt.weekly.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class InputConsole implements Input {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String getUserInput() {
        return sc.nextLine();
    }
}
