package org.prgrms.kdt.forward.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConsoleInput implements Input {

    private final BufferedReader reader;

    public ConsoleInput() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine() {
        String input = "";
        try {
            input = reader.readLine();
        } catch (IOException ignored) {
        }
        return input;
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException ignored) {
        }
    }
}