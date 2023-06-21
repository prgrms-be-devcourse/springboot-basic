package com.devmission.springbootbasic.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleView implements View {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String inputReadLine() throws IOException {
        return reader.readLine();
    }

}
