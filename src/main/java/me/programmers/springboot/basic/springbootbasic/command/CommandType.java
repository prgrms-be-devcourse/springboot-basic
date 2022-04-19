package me.programmers.springboot.basic.springbootbasic.command;

import java.util.Arrays;

public enum CommandType {
    EXIT, CREATE, LIST;

    public static CommandType getCommand(String menuCommand) {
        return Arrays.stream(CommandType.values())
                .filter(command -> command.name().equals(menuCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 명령어 입력 " + menuCommand));
    }
}
