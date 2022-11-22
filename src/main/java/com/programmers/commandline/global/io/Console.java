package com.programmers.commandline.global.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Console {

    public void print(String message) {
        System.out.print(message);
    }

    public String read() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String input = bufferedReader.readLine();
            return input;
        } catch (IOException e) {
            throw new RuntimeException(Message.READ_LINE.getMessage(), e);
        }
    }
}
