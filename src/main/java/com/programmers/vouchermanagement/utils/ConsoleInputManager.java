package com.programmers.vouchermanagement.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInputManager {

    private final Scanner scanner = new Scanner(System.in);

    public String inputString() {
        return scanner.nextLine();
    }

    public Long inputDiscount() {
        return Long.parseLong(scanner.nextLine());
    }
}
