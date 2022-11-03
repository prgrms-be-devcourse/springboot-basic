package org.prgrms.kdtspringdemo.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class ConsoleInput implements Input {
    private final Scanner myScanner = new Scanner(System.in);
    @Override
    public String getInput() {
        return myScanner.nextLine();
    }
}
