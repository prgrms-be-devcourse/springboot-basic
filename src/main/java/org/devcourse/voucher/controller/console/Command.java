package org.devcourse.voucher.controller.console;

import java.util.Arrays;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String commandName;

    Command(String commandName) {
        this.commandName = commandName;
    }

    public static Command find(String commandName) {
        String lowerCaseCommandName = commandName.toLowerCase();
        
        return Arrays.stream(values())
                .filter(command -> command.commandName.equals(lowerCaseCommandName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("지원하지 않는 명령 입니다"));
    }
}
