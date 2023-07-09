package com.programmers.application.controller.voucher.command;

import com.programmers.application.controller.voucher.command.execution.VoucherExecution;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CommandExecution {

    private final Map<Command, VoucherExecution> commandExecutionMap;

    public CommandExecution(Map<Command, VoucherExecution> commandExecutionMap) {
        this.commandExecutionMap = commandExecutionMap;
    }

    public void executeVoucher(Command command) throws IOException {
        VoucherExecution voucherExecution = commandExecutionMap.get(command);
        voucherExecution.execute();
    }
}
