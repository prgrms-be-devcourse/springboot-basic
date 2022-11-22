package com.programmers.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Input {
    private final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String input() throws IOException {
        return br.readLine();
    }

    public long inputNumber() throws IOException, NumberFormatException {
        return Long.parseLong(br.readLine());
    }
}
