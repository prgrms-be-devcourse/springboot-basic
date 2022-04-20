package me.programmers.springboot.basic.springbootbasic.command;

import java.util.Arrays;

public enum CommandType {
    EXIT, CREATE, LIST, CUSTOMER_INSERT, CUSTOMER_UPDATE,
    CUSTOMER_LIST, CUSTOMER_FINDBY_ID, CUSTOMER_FINDBY_NAME,
    CUSTOMER_FINDBY_EMAIL, CUSTOMER_DELETE;

    public static CommandType getCommand(String menuCommand) {
        return Arrays.stream(CommandType.values())
                .filter(command -> command.name().equals(menuCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 명령어 입력 " + menuCommand));
    }
}
