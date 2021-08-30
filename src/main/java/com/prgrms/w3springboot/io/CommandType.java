package com.prgrms.w3springboot.io;

import com.prgrms.w3springboot.io.strategy.CommandStrategy;
import com.prgrms.w3springboot.io.strategy.CreateCommand;
import com.prgrms.w3springboot.io.strategy.ExitCommand;
import com.prgrms.w3springboot.io.strategy.ListCommand;
import com.prgrms.w3springboot.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CommandType {
    CREATE("create", CreateCommand::new),
    LIST("list", ListCommand::new),
    EXIT("exit", ExitCommand::new);

    private final String command;
    private final Supplier<CommandStrategy> supplier;

    CommandType(String command, Supplier<CommandStrategy> supplier) {
        this.command = command;
        this.supplier = supplier;
    }

    public static CommandType of(final String commandType) {
        return Arrays.stream(CommandType.values())
                .filter(c -> c.isCommandType(commandType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean isCommandType(final String commandType) {
        return this.command.equals(commandType);
    }

    public boolean execute(Console console, VoucherService voucherService) {
        return this.supplier.get().execute(console, voucherService);
    }

}