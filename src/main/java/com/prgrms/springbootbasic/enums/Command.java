package com.prgrms.springbootbasic.enums;

import java.util.Arrays;

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");
    private final String inputCommand;

    Command(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    public static Command checkInputCommand(String inputCommand) {
        return Arrays.stream(values())
                .filter(e -> e.inputCommand.equalsIgnoreCase(inputCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입력입니다. 다시 입력해주세요." + inputCommand));
    }
}
