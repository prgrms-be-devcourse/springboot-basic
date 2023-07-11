package com.devcourse.voucherapp.view.io;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInput implements Input {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public String inputWithTrimming() {
        return input().trim();
    }

    @Override
    public String input() {
        return SCANNER.nextLine();
    }
}
