package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CommandStatus {
    EXIT("exit", () -> new ExitCommand()),
    LIST("list", () -> new ListCommand()),
    CREATE("create", () -> new CreateCommand());

    private final String command;
    private final Supplier<CommandStrategy> supplier;

    CommandStatus(final String command, final Supplier<CommandStrategy> supplier) {
        this.command = command;
        this.supplier = supplier;
    }

    public static boolean execute(Input input, Output output, VoucherService voucherService, String userCommand) {
        CommandStatus userCommandStatus = Arrays.stream(CommandStatus.values())
                .filter(commandStatus -> commandStatus.command.equals(userCommand))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Select one of three things: 'exit', 'list', 'create'."));
        return userCommandStatus.supplier.get().execute(input, output, voucherService);
    }

}
