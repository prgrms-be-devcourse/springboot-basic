package com.programmers.application.controller.voucher.command;

import com.programmers.application.controller.voucher.command.execution.CreateVoucherExecution;
import com.programmers.application.controller.voucher.command.execution.ExitVoucherExecution;
import com.programmers.application.controller.voucher.command.execution.ListVoucherExecution;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandExecution {

    private final CreateVoucherExecution createVoucherExecution;
    private final ExitVoucherExecution exitVoucherExecution;
    private final ListVoucherExecution listVoucherExecution;

    public CommandExecution(CreateVoucherExecution createVoucherExecution, ExitVoucherExecution exitVoucherExecution, ListVoucherExecution listVoucherExecution) {
        this.createVoucherExecution = createVoucherExecution;
        this.exitVoucherExecution = exitVoucherExecution;
        this.listVoucherExecution = listVoucherExecution;
    }

    public void executeVoucher(Command command) throws IOException {
        if (command == Command.CREATE) {
            createVoucherExecution.execute();
        }
        if (command == Command.EXIT) {
            exitVoucherExecution.execute();
        }
        if (command == Command.LIST) {
            listVoucherExecution.execute();
        }
    }
}
