package com.programmers.application.controller.voucher.command;

import com.programmers.application.controller.voucher.command.execution.CreateVoucherExecution;
import com.programmers.application.controller.voucher.command.execution.ExitVoucherExecution;
import com.programmers.application.controller.voucher.command.execution.ListVoucherExecution;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class VoucherExecutionDependencyInjector {

    private final CreateVoucherExecution createVoucherExecution;
    private final ExitVoucherExecution exitVoucherExecution;
    private final ListVoucherExecution listVoucherExecution;

    public VoucherExecutionDependencyInjector(CreateVoucherExecution createVoucherExecution, ExitVoucherExecution exitVoucherExecution, ListVoucherExecution listVoucherExecution) {
        this.createVoucherExecution = createVoucherExecution;
        this.exitVoucherExecution = exitVoucherExecution;
        this.listVoucherExecution = listVoucherExecution;
    }

    @PostConstruct
    public void inject() {
        for (Command command : Command.values()) {
            if (command.equals(Command.CREATE)) {
                command.setVoucherExecution(createVoucherExecution);
            }
            if (command.equals(Command.EXIT)) {
                command.setVoucherExecution(exitVoucherExecution);
            }
            if (command.equals(Command.LIST)) {
                command.setVoucherExecution(listVoucherExecution);
            }
        }
    }
}
