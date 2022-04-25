package org.prgrms.kdt.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputConsole implements Output {

    private static final Logger logger = LoggerFactory.getLogger(OutputConsole.class);

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printWarnMessage(Exception e) {
        logger.warn(e.getMessage(), e);
        printMessage(e.getMessage());
    }

    @Override
    public void printErrorMessage(Exception e) {
        logger.error(e.getMessage(), e);
        printMessage(e.getMessage());
    }
}
