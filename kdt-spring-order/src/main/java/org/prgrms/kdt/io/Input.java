package org.prgrms.kdt.io;

import java.io.BufferedReader;
import java.io.IOException;

public class Input {

    private final BufferedReader reader;

    public Input(BufferedReader reader) {
        this.reader = reader;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

}
