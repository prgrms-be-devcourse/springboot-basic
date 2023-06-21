package com.devmission.springbootbasic.view;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConsoleView implements View {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String inputReadLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

}
