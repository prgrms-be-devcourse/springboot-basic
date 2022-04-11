package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CmdList {
    EXIT("exit", ExitCommand::new),
    CREATE("create", CreateCommand::new),
    LIST("list", ListCommand::new);

    private final String command;
    private final Supplier<Command> supplier;
    CmdList(String command, Supplier<Command> supplier) {
        this.command = command;
        this.supplier = supplier;
    }

    public static boolean execute(String inputCommand, Input input, Output output, VoucherService voucherService) {

        CmdList cmdList = Arrays.stream(CmdList.values())
                .filter(cmd -> cmd.command.equalsIgnoreCase(inputCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no '" + inputCommand + "'. "));

        return cmdList.supplier.get().execute(input, output, voucherService);
    }
}
