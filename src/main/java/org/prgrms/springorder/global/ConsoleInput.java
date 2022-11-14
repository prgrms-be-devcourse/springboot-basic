package org.prgrms.springorder.global;

import java.io.BufferedReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ConsoleInput implements Input {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleInput.class);

    private final BufferedReader bufferedReader;

    public ConsoleInput(BufferedReader reader) {
        this.bufferedReader = reader;
    }

    @Override
    public String input() {
        try {
            String input = bufferedReader.readLine();
            validateEmptyInput(input);
            return input;
        } catch (IOException e) {
            logger.error("inputString error", e);
            throw new IllegalArgumentException("잘못된 입력입니다.", e);
        }
    }

    private void validateEmptyInput(String input) {
        if (!StringUtils.hasText(input)) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

}