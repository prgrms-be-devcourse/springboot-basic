package org.prgrms.dev.command;

import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CommandType {
    CREATE("create", CreateCommand::new),
    LIST("list", ListCommand::new),
    UPDATE("update", UpdateCommand::new),
    DELETE("delete", DeleteCommand::new),
    EXIT("exit", ExitCommand::new);

    private final String command;
    private final Supplier<Command> supplier;

    CommandType(String command, Supplier<Command> supplier) {
        this.command = command;
        this.supplier = supplier;
    }

    public static boolean execute(String inputCommandType, Input input, Output output, VoucherService voucherService) {
        CommandType commandType = Arrays.stream(CommandType.values())
                .filter(cmd -> cmd.command.equalsIgnoreCase(inputCommandType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입을 입력하였습니다."));
        return commandType.supplier.get()
                .execute(input, output, voucherService);
    }
}
