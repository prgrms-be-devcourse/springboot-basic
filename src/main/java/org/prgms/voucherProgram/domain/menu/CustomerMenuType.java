package org.prgms.voucherProgram.domain.menu;

import java.util.Arrays;

import org.prgms.voucherProgram.exception.WrongCommandException;

public enum CustomerMenuType {
    EXIT("exit"),
    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete");

    private final String command;

    CustomerMenuType(String command) {
        this.command = command;
    }

    public static CustomerMenuType from(String command) {
        return Arrays.stream(CustomerMenuType.values())
            .filter(type -> type.command.equalsIgnoreCase(command))
            .findFirst()
            .orElseThrow(WrongCommandException::new);
    }
}
