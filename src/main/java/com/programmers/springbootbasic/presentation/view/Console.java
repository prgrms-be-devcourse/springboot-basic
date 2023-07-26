package com.programmers.springbootbasic.presentation.view;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class Console implements Input, Output {
    private static final String NEW_LINE = "\n";

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    @Override
    public String inputLine() throws IOException {
        return br.readLine();
    }

    @Override
    public void print(String input) throws IOException {
        bw.write(input);
        bw.flush();
    }

    @Override
    public void printLine(String input) throws IOException {
        bw.write(input);
        bw.write(NEW_LINE);
        bw.flush();
    }
}
