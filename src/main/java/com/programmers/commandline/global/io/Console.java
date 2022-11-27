package com.programmers.commandline.global.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

@Component
public class Console {

    private static final Pattern numberPattern = Pattern.compile("^[0-9]*$");
    private final Logger logger = LoggerFactory.getLogger(Console.class);

    public void print(String message) {
        System.out.print(message);
    }

    public String read() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new ConsoleException(Message.READ_LINE.getMessage(), e);
        }
    }

    public String readNumber() {
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.next();
            validateNumber(input);
            return input;
        } catch (NoSuchElementException | IllegalStateException e) {
            logger.info(Message.INT_READ_EXCEPTION.getMessage());
            throw new ConsoleException(Message.READ_LINE.getMessage(), e);
        }
    }

    private void validateNumber(String input) {
        if (!numberPattern.matcher(input).matches()) {
            throw new IllegalArgumentException(Message.VALIDATE_PARSE_TO_NUMBER_ERROR.getMessage());
        }
    }
}
