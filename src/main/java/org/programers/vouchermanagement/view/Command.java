package org.programers.vouchermanagement.view;

import java.util.Arrays;

public enum Command {
    BLACKLIST("blacklist"), CREATE_VOUCHER("createVoucher"), LIST_VOUCHER("listVoucher"), EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command from(String command) {
        return Arrays.stream(values())
                .filter(menu -> menu.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어입니다. : " + command));
    }

    public boolean isBlacklist() {
        return this.equals(Command.BLACKLIST);
    }

    public boolean isCreateVoucher() {
        return this.equals(Command.CREATE_VOUCHER);
    }

    public boolean isListVoucher() {
        return this.equals(Command.LIST_VOUCHER);
    }

    public boolean isExit() {
        return this.equals(Command.EXIT);
    }

    public String getCommand() {
        return command;
    }
}
