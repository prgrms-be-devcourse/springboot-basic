package org.prgms.voucherProgram.domain.menu;

import java.util.stream.Stream;

import org.prgms.voucherProgram.exception.WrongCommandException;

public enum CustomerMenuType {
    EXIT("exit"),
    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete"),
    ALL("all"),
    JUST_ONE("one"),
    BLACKLIST("blacklist");

    private final String command;

    CustomerMenuType(String command) {
        this.command = command;
    }

    public static CustomerMenuType from(String command) {
        return Stream.of(CREATE, READ, UPDATE, DELETE, EXIT)
            .filter(type -> type.command.equalsIgnoreCase(command))
            .findFirst()
            .orElseThrow(WrongCommandException::new);
    }

    public static CustomerMenuType fromSubMenu(String command) {
        return Stream.of(ALL, JUST_ONE, BLACKLIST)
            .filter(type -> type.command.equalsIgnoreCase(command))
            .findFirst()
            .orElseThrow(WrongCommandException::new);
    }
}
