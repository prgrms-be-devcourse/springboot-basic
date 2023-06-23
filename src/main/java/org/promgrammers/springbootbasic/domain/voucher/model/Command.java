package org.promgrammers.springbootbasic.domain.voucher.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum Command {

    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist");

    private final String command;
    private static final Logger logger = LoggerFactory.getLogger(Command.class);

    Command(String command) {
        this.command = command;
    }

    public static Command of(String inputCommand) {
        try {
            return Arrays.stream(values())
                    .filter(value -> value.command.equals(inputCommand.toLowerCase()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입력입니다"));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid Command Select => {}", inputCommand);
            throw e;
        }
    }
}
