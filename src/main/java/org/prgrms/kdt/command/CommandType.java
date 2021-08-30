package org.prgrms.kdt.command;

import org.prgrms.kdt.command.domain.Command;
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
    CREATE("create", StrategyCreate::new),
    EXIT("exit", StrategyExit::new),
    LIST("list", StrategyList::new);

    private final String inputCommand;
    private final Supplier<Command> supplier;
    private static Map<String, CommandType> commandMap = Arrays
            .stream(CommandType.values())
            .collect(Collectors.toMap(o -> o.inputCommand, o -> o)); // TODO: Group By

    CommandType(String inputCommand, Supplier<Command> supplier) {
        this.inputCommand = inputCommand;
        this.supplier = supplier;
    }

    public boolean execute(Input input, Output output, VoucherService voucherService) {
        return this.supplier.get().execute(input, output, voucherService);
    }

    public static Optional<CommandType> findCommand(String inputCommandType) { // TODO: values()
        // 1. if null throw // optional 의 비용이 큰 것 같음.
        return Optional // Optional 을 쓰는 이유x
                .ofNullable(commandMap.getOrDefault(inputCommandType, null));
                // .orElseThrow(IllegalArgumentException::new);
    }
}
