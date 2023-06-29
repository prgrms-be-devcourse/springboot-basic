package org.promgrammers.springbootbasic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandProgramStatus {

    private static boolean isRunning = true;
    private static final Logger logger = LoggerFactory.getLogger(CommandProgramStatus.class);

    private CommandProgramStatus() throws InstantiationException {
        logger.error("Cannot instantiate.");
        throw new InstantiationException("인스턴스화 할 수 없습니다.");
    }

    public static void stop() {
        isRunning = false;
    }

    public static boolean isRunning() {
        return isRunning;
    }
}