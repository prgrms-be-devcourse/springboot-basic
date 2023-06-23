package org.weekly.weekly.ui.reader;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@Primary
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
