package org.prgms.management.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {
    private static final Scanner sc = new Scanner(System.in);

    @Override
    public String getInput(String text) {
        System.out.print(text);
        return sc.nextLine();
    }
}
