package org.prgrms.kdt.io.file;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;

public class TxtFileIO implements IO {

    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public TxtFileIO(BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        this.bufferedReader = bufferedReader;
        this.bufferedWriter = bufferedWriter;
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public void writeLine(String s) throws IOException {
        bufferedWriter.write(s + System.lineSeparator());
        bufferedWriter.flush();
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
            bufferedReader.mark(10000000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void close() {
        try {
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
