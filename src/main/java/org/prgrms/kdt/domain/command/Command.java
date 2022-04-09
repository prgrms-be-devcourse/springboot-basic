package org.prgrms.kdt.domain.command;

import java.util.Arrays;

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command findCommand(String command) {
        return Arrays.stream(values())
                .filter(value -> value.command.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 명령어입니다."));
    }
}
