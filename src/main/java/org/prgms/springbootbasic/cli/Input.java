package org.prgms.springbootbasic.cli;

import org.prgms.springbootbasic.exception.CommandLineIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Input implements CommandLineIO {
    private final Logger logger = LoggerFactory.getLogger(Input.class);
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public String read() {
        try {
            String input = this.bufferedReader.readLine();
            return validateInput(input);
        } catch (IOException e) {
            throw new CommandLineIOException("error occurred reading line with buffered reader", e);
        }
    }

    private String validateInput(String input) {
        if (input == null) {
            logger.error("unable to read input through buffered reader input : null");
        } else if (input.isEmpty()) {
            logger.error("empty input entered | input : {}", input);
        } else if (!input.matches("\\w+")) {
            logger.error("not valid token exists | input : {}", input);
        } else {
            return input.replace(" ", "");
        }

        return input;
    }

    @Override
    public void stop() {
        try {
            this.bufferedReader.close();
        } catch (IOException e) {
            throw new CommandLineIOException("unable to close buffered reader", e);
        }
    }
}
