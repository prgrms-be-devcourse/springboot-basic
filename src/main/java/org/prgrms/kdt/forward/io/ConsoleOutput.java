package org.prgrms.kdt.forward.io;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ConsoleOutput implements Output {

    private final BufferedWriter writer;

    public ConsoleOutput() {
        this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public void write(String string) {
        try {
            writer.write(string + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}