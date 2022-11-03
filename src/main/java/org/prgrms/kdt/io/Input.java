package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {
    private final Scanner scanner = new Scanner(System.in);
    public String inputText() {
        return scanner.nextLine();
    }
}