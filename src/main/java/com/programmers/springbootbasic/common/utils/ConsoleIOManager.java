package com.programmers.springbootbasic.common.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConsoleIOManager {
    private final BufferedReader bufferedReader;

    public ConsoleIOManager() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void print(String s) {
        System.out.print(s);
    }

    public void println(String s) {
        System.out.println(s);
    }

    public void printSystemMsg(String s) {
        println("[System] %s\n".formatted(s));
    }

    public String getInput() throws IOException {
        print("> ");
        String input = bufferedReader.readLine();
        print("\n");
        return input;
    }
}
