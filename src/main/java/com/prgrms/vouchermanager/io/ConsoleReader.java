package com.prgrms.vouchermanager.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReader {
    private final Scanner sc = new Scanner(System.in);

    public String readString() {
        return sc.nextLine();
    }

    public int readInt() throws NumberFormatException {
        int num = sc.nextInt();
        sc.nextLine();
        return num;
    }
}
