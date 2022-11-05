package com.programmers.commandLine.global.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

@Component
public class Console {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void print(String message) {
        System.out.print(MessageFormat.format("{0}", message));
    }

    public String read() {
        try {
            return br.readLine();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
