package com.voucher.vouchermanagement.manager.command;

import java.util.Arrays;

public enum CommandType {
    NONE("none", "empty command"),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list", "Type list to list all vouchers."),
    BLACKLIST("blacklist", "Type blacklist to list all blacklist"),
    EXIT("exit", "Type exit to exit program.");

    private final String commandName;
    private final String commandDescription;

    CommandType(String commandName, String commandDescription) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public static CommandType getCommandTypeByName(String nameInput) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> !(nameInput.equals(CommandType.NONE.commandName)) && commandType.commandName.equals(nameInput))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 커맨드 입력 입니다."));
    }
}
