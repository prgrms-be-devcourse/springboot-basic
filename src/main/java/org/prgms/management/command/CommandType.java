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

public enum CommandType {
    EXIT("exit", ExitCommand::new),
    CREATE("create", CreateCommand::new),
    LIST("list", ListCommand::new),
    BLACKLIST("blacklist", BlacklistCommand::new);

    private final String command;
    private final Supplier<Command> supplier;
    private static final Logger logger = LoggerFactory.getLogger(CommandType.class);

    CommandType(String command, Supplier<Command> supplier) {
        this.command = command;
        this.supplier = supplier;
    }

    public static boolean execute(String inputCommandType, Input input, Output output,
                                  BlacklistService blacklistService, VoucherService voucherService) {
        Optional<CommandType> commandType = Arrays.stream(CommandType.values())
                .filter(cmt -> cmt.command.equalsIgnoreCase(inputCommandType))
                .findFirst();
        if (commandType.isEmpty()) {
            logger.error("잘못된 명령어를 입력했습니다. 입력한 명령어 -> {}\n", inputCommandType);
            return true;
        }
        return commandType.get().supplier.get().execute(input, output, voucherService, blacklistService);
    }
}
