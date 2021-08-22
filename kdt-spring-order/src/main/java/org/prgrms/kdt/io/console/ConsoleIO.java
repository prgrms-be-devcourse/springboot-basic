package org.prgrms.kdt.io.console;

import org.prgrms.kdt.io.file.IO;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.*;

public class ConsoleIO implements IO {

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ConsoleIO() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public void writeLine(String s) throws IOException {
        bufferedWriter.write(s +"\n");
        bufferedWriter.flush();
    }

    @Override
    public void reset() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
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
