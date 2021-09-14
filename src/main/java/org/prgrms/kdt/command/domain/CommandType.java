package org.prgrms.kdt.command.domain;

import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.command.service.CreateCommandService;
import org.prgrms.kdt.command.service.ExitCommandService;
import org.prgrms.kdt.command.service.ListCommandService;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CommandType {
    CREATE("create"),
    EXIT("exit"),
    LIST("list"),
    BLACKLIST("blacklist");

    private final String strCommand;

    CommandType(String strCommand) {
        this.strCommand = strCommand;
    }

    public static CommandType findCommand(String inputCommandType) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.strCommand.equals(inputCommandType))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
