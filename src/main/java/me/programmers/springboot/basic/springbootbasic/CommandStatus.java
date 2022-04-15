package me.programmers.springboot.basic.springbootbasic;

import java.util.Arrays;

public enum CommandStatus {
    EXIT, CREATE, LIST;

    public static CommandStatus getCommand(String menuCommand) {
        return Arrays.stream(CommandStatus.values())
                .filter(e -> e.name().equals(menuCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 명령어"));
    }
}
