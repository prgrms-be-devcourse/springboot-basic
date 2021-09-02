package org.prgrms.kdt.command;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.command.strategy.StrategyCreate;
import org.prgrms.kdt.command.strategy.StrategyExit;
import org.prgrms.kdt.command.strategy.StrategyList;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CommandType {
    CREATE("create", StrategyCreate::new),
    EXIT("exit", StrategyExit::new),
    LIST("list", StrategyList::new);

    private final String strCommand;
    private final Supplier<Command> supplier;

    CommandType(String strCommand, Supplier<Command> supplier) {
        this.strCommand = strCommand;
        this.supplier = supplier;
    }

    public static CommandType findCommand(String inputCommandType) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.strCommand.equals(inputCommandType))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean execute(Input input, Output output, VoucherService voucherService) {
        return this.supplier.get().execute(input, output, voucherService);
    }
}
