package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CommandType {
    EXIT("exit", ExitCommand::new),
    CREATE("create", CreateCommand::new),
    LIST("list", ListCommand::new),
    UPDATE("update", UpdateCommand::new),
    DELETE("delete", DeleteCommand::new);

    private final String command;
    private final Supplier<Command> commandSupplier;

    CommandType(String command, Supplier<Command> commandSupplier) {
        this.command = command;
        this.commandSupplier = commandSupplier;
    }

    public static boolean execute(String input, Console console, VoucherService voucherService) {
        CommandType commandType =  Arrays.stream(CommandType.values())
                .filter(type -> type.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 커맨드 타입입니다."));
        return commandType.commandSupplier.get()
                .execute(console, voucherService);
    }

}
