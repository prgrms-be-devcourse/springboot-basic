package com.example.voucher.io;

import java.util.Scanner;

class Reader {

    private final Scanner scanner;

    public Reader() {
        this.scanner = new Scanner(System.in);
    }

    public String readString() {
        return scanner.nextLine();
    }

    public Integer readInteger() {
        String input = scanner.nextLine();

        return  Integer.parseInt(input);
    }

    public Long readLong() {
        String input = scanner.nextLine();

        return Long.parseLong(input);
    }

}
