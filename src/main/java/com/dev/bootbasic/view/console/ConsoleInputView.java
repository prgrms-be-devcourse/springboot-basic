package com.dev.bootbasic.view.console;

import com.dev.bootbasic.view.InputView;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConsoleInputView implements InputView {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String inputLine() throws IOException {
        return reader.readLine();
    }

}
