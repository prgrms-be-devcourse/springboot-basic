package com.programmers.vouchermanagement.infra.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class ConsoleInput {
    private final Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    public int readInt(String prompt, String errorMessage) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public long readLong(String prompt, String errorMessage) {
        System.out.print(prompt);
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public UUID readUUID(String prompt, String errorMessage) {
        System.out.print(prompt);
        try {
            return UUID.fromString(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
