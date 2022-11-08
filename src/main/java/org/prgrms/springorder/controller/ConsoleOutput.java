package org.prgrms.springorder.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleOutput implements Output {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    private final BufferedWriter bufferedWriter;

    public ConsoleOutput(BufferedWriter writer) {
        this.bufferedWriter = writer;
    }

    @Override
    public void showMessages(String... message) {
        Arrays.stream(message).forEach(s -> {
            try {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                logger.error("showMessage write error", e);
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }

}
