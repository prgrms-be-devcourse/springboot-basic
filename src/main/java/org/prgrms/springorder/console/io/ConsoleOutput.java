package org.prgrms.springorder.console.io;

import java.io.BufferedWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleOutput implements Output {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    private final BufferedWriter bufferedWriter;

    public ConsoleOutput(BufferedWriter writer) {
        this.bufferedWriter = writer;
    }

    @Override
    public void showMessages(String[] messages) {
        showMessage("");
        for (String message : messages) {
            showMessage(message);
        }
    }

    @Override
    public void showMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("showMessage write error", e);
            throw new RuntimeException (e.getMessage(), e);
        }
    }


}
