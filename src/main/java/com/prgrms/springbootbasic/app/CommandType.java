package com.prgrms.springbootbasic.app;

import com.prgrms.springbootbasic.common.exception.InvalidCommandTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;

enum CommandType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private static final Logger logger = LoggerFactory.getLogger(CommandType.class);
    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    /**
     * @throws InvalidCommandTypeException CommandType에 해당하지 않는 입력이 주어졌을 때 발생하는 예외입니다.
     */
    public static CommandType from(String inputCommand) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.getCommand().equals(inputCommand))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("InvalidCommandTypeException occurred when getting command from console. invalid command input was provided.");
                    return new InvalidCommandTypeException(
                            MessageFormat.format("Input command {0} is invalid. Please select again.", inputCommand));
                });
    }
}
