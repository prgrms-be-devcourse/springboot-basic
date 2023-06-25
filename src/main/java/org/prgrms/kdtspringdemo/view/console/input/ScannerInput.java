package org.prgrms.kdtspringdemo.view.console.input;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerInput implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
