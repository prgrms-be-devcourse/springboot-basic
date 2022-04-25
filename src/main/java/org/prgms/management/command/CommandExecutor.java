package org.prgms.management.command;

import org.prgms.management.blacklist.service.BlacklistService;
import org.prgms.management.io.Input;
import org.prgms.management.io.Output;
import org.prgms.management.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

public enum CommandExecutor {
    EXIT("exit", ExitCommand::new),
    CREATE("create", CreateCommand::new),
    LIST("list", ListCommand::new),
    BLACKLIST("blacklist", BlacklistCommand::new);

    private final String command;
    private final Supplier<Command> supplier;
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    CommandExecutor(String command, Supplier<Command> supplier) {
        this.command = command;
        this.supplier = supplier;
    }

    public static boolean execute(String inputCommandType, Input input, Output output,
                                  BlacklistService blacklistService, VoucherService voucherService) {
        Optional<CommandExecutor> commandExecutor = Arrays.stream(CommandExecutor.values())
                .filter(cmt -> cmt.command.equalsIgnoreCase(inputCommandType))
                .findFirst();

        if (commandExecutor.isEmpty()) {
            logger.error("잘못된 명령어를 입력했습니다. 입력한 명령어 -> {}", inputCommandType);
            return true;
        }

        return commandExecutor.get().supplier.get().execute(input, output, voucherService, blacklistService);
    }
}
