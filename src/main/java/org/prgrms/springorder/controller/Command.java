package org.prgrms.springorder.controller;

import java.util.Arrays;
import java.util.Objects;

public enum Command {

    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("black-list")
    ;

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command of(String inputCommand) {
        return Arrays.stream(values())
            .filter(e -> Objects.equals(e.command, inputCommand.toLowerCase()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입력입니다. input : " + inputCommand));
    }

}
