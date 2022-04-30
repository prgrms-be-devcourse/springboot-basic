package org.prgrms.deukyun.voucherapp.view.console;

import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * 콘솔 서비스
 */
@Service
public class ConsoleService {

    private final static String ANSI_YELLOW = "\u001B[33m";
    private final static String ANSI_RESET = "\u001B[0m";

    private final Scanner scanner = new Scanner(System.in);

    public String readLine() {
        return scanner.nextLine();
    }

    public long readLong() {
        try {
            return Long.parseLong(readLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("You should Enter Number");
        }
    }

    public void write(String msg, String... args) {
        System.out.print(ANSI_YELLOW);
        System.out.printf(msg, (Object[]) args);
        System.out.print(ANSI_RESET);
        System.out.println();
    }

}
