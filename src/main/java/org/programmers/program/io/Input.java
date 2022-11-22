package org.programmers.program.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Input {
    private final BufferedReader bufferedReader;

    public Input() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String input(String prompt) throws IOException {
        System.out.print(prompt);
        return this.bufferedReader.readLine();
    }
}
