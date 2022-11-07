package org.prgrms.springorder.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleOutput {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    private final BufferedWriter bufferedWriter;

    public ConsoleOutput(BufferedWriter writer) {
        this.bufferedWriter = writer;
    }

    public void showMessage(String... message) {
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

    public void showMessages(List<String> messages) {

        if (messages.isEmpty()) {
            try {
                bufferedWriter.write("저장된 바우처가 없습니다.");
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                logger.error("showMessages write error", e);
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        messages.forEach(message -> {
            try {
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                logger.error("showMessage write error", e);
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }

}
