package com.programmers.application.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

@Component
public class Console implements IO{
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    @Override
    public void write(String input) throws IOException {
        bufferedWriter.write(input);
        bufferedWriter.flush();
    }

    @Override
    public String read() throws IOException {
       return bufferedReader.readLine();
    }
}
