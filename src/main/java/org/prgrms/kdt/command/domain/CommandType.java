package org.prgrms.kdt.command.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum CommandType {
    CREATE("create"),
    EXIT("exit"),
    LIST("list"),
    BLACK_LIST("blacklist"),;


    private final String inputCommand;
    private static Map<String, CommandType> commandMap = Arrays
            .stream(CommandType.values())
            .collect(Collectors.toMap(o -> o.inputCommand, o -> o)); // TODO: Group By

    CommandType(String inputCommand) {
        this.inputCommand = inputCommand;
    }


    public static CommandType findCommand(String inputCommandType) { // TODO: values()
        // 1. if null throw // optional 의 비용이 큰 것 같음.
        return Optional // Optional 을 쓰는 이유x
                .ofNullable(commandMap.getOrDefault(inputCommandType, null))
                .orElseThrow(IllegalArgumentException::new);
    }
}
