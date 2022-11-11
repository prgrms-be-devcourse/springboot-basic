package org.prgms.springbootbasic.cli;

import org.prgms.springbootbasic.exception.CommandLineIOException;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

@Component
public class Output implements CommandLineIO {
    private final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(String message) {
        try {
            bufferedWriter.write(message + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new CommandLineIOException("failed to write commandline", e);
        }
    }

    public void print(String[] messageArr) {
        try {
            for (String message : messageArr) {
                bufferedWriter.write(message + "\n");
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new CommandLineIOException("failed to write commandline", e);
        }

    }

    @Override
    public void stop() {
        try {
            this.bufferedWriter.flush();
            this.bufferedWriter.close();
        } catch (IOException e) {
            throw new CommandLineIOException("failed to stop buffered writer", e);
        }

    }

}
