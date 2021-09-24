package org.prgrms.kdt.command.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum CommandType {
    CREATE("create"),
    EXIT("exit"),
    LIST("list"),
    BLACKLIST("blacklist");

    private static final Logger logger = LoggerFactory.getLogger(CommandType.class);

    private final String strCommand;

    CommandType(String strCommand) {
        this.strCommand = strCommand;
    }

    public static CommandType findCommand(String inputCommandType) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.strCommand.equals(inputCommandType))
                .findAny()
                .orElseThrow(() -> {
                    String errorMessage = "Can't find command type";
                    logger.error(errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }
}
