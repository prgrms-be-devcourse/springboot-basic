package org.prgrms.springorder.controller;

import java.util.Arrays;

public enum Command {

    EXIT("exit"),
    CREATE("create"),
    LIST("list")
    ;

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command of(String inputCommand) {
        return Arrays.stream(values())
            .filter(e -> e.command.equals(inputCommand.toLowerCase()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입력입니다. input : " + inputCommand));
    }

}
