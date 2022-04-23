package org.prgms.voucherProgram.console.menu;

import java.util.stream.Stream;

import org.prgms.voucherProgram.exception.WrongCommandException;

public enum VoucherMenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    UPDATE("update"),
    DELETE("delete"),
    WALLET("wallet"),
    ASSIGN("assign"),
    FIND("find");

    private final String command;

    VoucherMenuType(String command) {
        this.command = command;
    }

    public static VoucherMenuType fromMainMenu(String command) {
        return Stream.of(EXIT, CREATE, LIST, UPDATE, DELETE, WALLET)
            .filter(type -> type.command.equalsIgnoreCase(command))
            .findFirst()
            .orElseThrow(WrongCommandException::new);
    }

    public static VoucherMenuType fromSubMenu(String command) {
        return Stream.of(EXIT, ASSIGN, LIST, FIND, DELETE)
            .filter(type -> type.command.equalsIgnoreCase(command))
            .findFirst()
            .orElseThrow(WrongCommandException::new);
    }
}
