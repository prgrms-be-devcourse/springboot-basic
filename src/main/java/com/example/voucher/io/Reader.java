package com.example.voucher.io;

import java.util.Scanner;

public class Reader {

    private final Scanner scanner;

    public Reader() {
        this.scanner = new Scanner(System.in);

    }

    public String readString() {
        String input = scanner.nextLine();

        return input;

    }

    public Integer readInteger() {
        String input = scanner.nextLine();
        Integer num = Integer.parseInt(input);

        return num;

    }

    public Long readLong() {
        String input = scanner.nextLine();
        Long num = Long.parseLong(input);

        return num;

    }

}
