package org.prgrms.kdtspringdemo.view;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class InputConsole {
    private final BufferedReader br;

    public InputConsole() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getString() throws IOException {
        String fun = br.readLine();
        return fun;
    }
}
