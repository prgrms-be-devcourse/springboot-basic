package org.prgms.voucherProgram.domain.menu;

import java.util.Arrays;

import org.prgms.voucherProgram.exception.WrongCommandException;

public enum VoucherMenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    UPDATE("update");

    private final String command;

    VoucherMenuType(String command) {
        this.command = command;
    }

    public static VoucherMenuType from(String command) {
        return Arrays.stream(VoucherMenuType.values())
            .filter(type -> type.command.equalsIgnoreCase(command))
            .findFirst()
            .orElseThrow(WrongCommandException::new);
    }
}
