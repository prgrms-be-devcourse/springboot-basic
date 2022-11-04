package prgms.voucherapplication.io;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {
    private final BufferedReader bufferedReader;

    public Reader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
