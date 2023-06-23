package org.weekly.weekly.ui.reader;

import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.reader.CommandReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class BufferedReaderWrap implements CommandReader {
    private final BufferedReader bufferedReader;

    public BufferedReaderWrap() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }
    @Override
    public String readLine() throws IOException {
        return this.bufferedReader.readLine();
    }
}
