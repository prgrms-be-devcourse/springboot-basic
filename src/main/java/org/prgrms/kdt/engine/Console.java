package org.prgrms.kdt.engine;

import org.prgrms.kdt.engine.io.Input;
import org.prgrms.kdt.engine.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Console implements Input, Output {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String input(String prompt) throws IOException {
        System.out.println(prompt);
        return br.readLine();
    }

    @Override
    public void logInfo(String output) {
        logger.info(output);
    }

    @Override
    public void logError(String output) {
        logger.error(output);
    }

    @Override
    public void printConsole(String output) {
        System.out.println(output);
    }
}
