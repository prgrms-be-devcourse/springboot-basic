package com.devmission.springbootbasic.view.console;

import java.util.Arrays;

public enum Command {

    CREATE,
    LIST,
    EXIT;

    private static final String INVALID_COMMAND_MESSAGE = "선택할 수 없는 명령입니다.";

    public static Command from(String name) {
        return Arrays.stream(values()).filter(command -> command.name().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND_MESSAGE));
    }

}
