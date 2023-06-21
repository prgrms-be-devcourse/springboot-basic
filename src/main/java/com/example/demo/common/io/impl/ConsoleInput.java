package com.example.demo.common.io.impl;

import com.example.demo.common.io.Input;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
