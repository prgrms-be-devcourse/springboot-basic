package org.prgrms.kdt.command;

import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.command.strategy.StrategyCreate;
import org.prgrms.kdt.command.strategy.StrategyExit;
import org.prgrms.kdt.command.strategy.StrategyList;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum CommandType {
    CREATE("create", () -> new StrategyCreate()),
    EXIT("exit", StrategyExit::new),
    LIST("list", () -> new StrategyList()),
    ;

    private final String inputCommand;
    private final Supplier<Command> supplier;
    private static Map<String, CommandType> commandMap = Arrays.stream(CommandType.values()).collect(Collectors.toMap(o -> o.inputCommand, o -> o));

    CommandType(String inputCommand, Supplier<Command> supplier) {
        this.inputCommand = inputCommand;
        this.supplier = supplier;
    }

    public boolean excute(Input input, Output output, VoucherService voucherService) {
        return this.supplier.get().excute(input, output, voucherService);
    }

    public static CommandType findCommand(String inputCommandType) {
        return Optional.ofNullable(commandMap.getOrDefault(inputCommandType, null)).orElseThrow(IllegalArgumentException::new);
    }
}
