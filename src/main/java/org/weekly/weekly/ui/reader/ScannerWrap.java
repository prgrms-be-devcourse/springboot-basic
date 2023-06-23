package org.weekly.weekly.ui.reader;

import java.io.IOException;
import java.util.Scanner;

public class ScannerWrap implements CommandReader{
    private final Scanner scanner;

    public ScannerWrap() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() throws IOException {
        return null;
    }
}
