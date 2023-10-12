package org.prgrms.kdtspringdemo.view;

import java.io.BufferedReader;
import java.io.IOException;

public class InputConsole {
    private final BufferedReader br;

    public InputConsole(BufferedReader br) {
        this.br = br;
    }

    public String getString() throws IOException {
        String fun = br.readLine();
        return fun;
    }
}
