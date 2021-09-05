package org.prgrms.kdt.io;

import java.io.BufferedReader;
import java.io.IOException;

public class CsvFileIO implements IO {

    private final BufferedReader bufferedReader;

    public CsvFileIO(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public void writeLine(String s) throws IOException {
        throw new IOException("Cannot write csv file");
    }

    @Override
    public void reset() {
        try {
            bufferedReader.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mark() {
        try {
            bufferedReader.mark(100000000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
