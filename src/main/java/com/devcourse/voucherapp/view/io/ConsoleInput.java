package com.devcourse.voucherapp.view.io;

import java.util.Scanner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class ConsoleInput implements Input {

    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String inputWithTrimming() {
        return input().trim();
    }

    @Override
    public String input() {
        return scanner.nextLine();
    }
}
