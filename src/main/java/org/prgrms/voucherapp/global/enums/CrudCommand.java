package org.prgrms.voucherapp.global.enums;

import java.util.Optional;

/*
 * Command : 커맨드를 enum으로 관리합니다.
 * */
public enum CrudCommand {
    CANCEL("cancel"),
    CREATE("create"),
    LIST("list"),
    UPDATE("update"),
    DELETE("delete");

    private final String command;

    CrudCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Optional<CrudCommand> getMenu(String command) {
        try {
            return Optional.of(CrudCommand.valueOf(command.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
