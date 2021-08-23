package org.prgrms.kdt.io.console;

import org.prgrms.kdt.io.file.IO;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.*;

public class ConsoleIO implements IO {

    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public ConsoleIO(BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
            bufferedReader.mark(100);
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
