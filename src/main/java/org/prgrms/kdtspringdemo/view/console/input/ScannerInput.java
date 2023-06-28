package org.prgrms.kdtspringdemo.view.console.input;

import java.util.Scanner;

public class ScannerInput implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
