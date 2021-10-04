package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CommandStatus {
    EXIT("exit", ExitCommand::new),
    LIST("list", ListCommand::new),
    CREATE("create", CreateCommand::new);

    private static final Logger logger = LoggerFactory.getLogger(CommandStatus.class);

    private final String command;
    private final Supplier<CommandStrategy> supplier;

    CommandStatus(String command, Supplier<CommandStrategy> supplier) {
        this.command = command;
        this.supplier = supplier;
    }

    public static boolean execute(Input input, Output output, VoucherService voucherService, String userCommand) {
        CommandStatus userCommandStatus = Arrays.stream(CommandStatus.values())
                .filter(commandStatus -> commandStatus.command.equals(userCommand))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Select one of three things: 'exit', 'list', 'create'.");
                    return new IllegalArgumentException("Select one of three things: 'exit', 'list', 'create'.");
                });
        return userCommandStatus.supplier.get().execute(input, output, voucherService);
    }

}
