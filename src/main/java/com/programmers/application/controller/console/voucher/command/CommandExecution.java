package com.programmers.application.controller.console.voucher.command;

import com.programmers.application.controller.console.voucher.command.execution.VoucherExecution;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CommandExecution {

    private final Map<VoucherCommand, VoucherExecution> voucherExecutionMap;

    public CommandExecution(Map<VoucherCommand, VoucherExecution> commandExecutionMap) {
        this.voucherExecutionMap = commandExecutionMap;
    }

    public void executeVoucher(VoucherCommand command) throws IOException {
        VoucherExecution voucherExecution = voucherExecutionMap.get(command);
        voucherExecution.execute();
    }
}
