package com.programmers.part1.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public void write(Object object) {
        System.out.println(object);
    }
}
