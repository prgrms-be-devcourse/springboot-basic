package com.programmers.application.config;

import com.programmers.application.controller.voucher.command.Command;
import com.programmers.application.controller.voucher.command.execution.CreateVoucherExecution;
import com.programmers.application.controller.voucher.command.execution.ExitVoucherExecution;
import com.programmers.application.controller.voucher.command.execution.ListVoucherExecution;
import com.programmers.application.controller.voucher.command.execution.VoucherExecution;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class VoucherExecutionMapConfiguration {

    private final CreateVoucherExecution createVoucherExecution;
    private final ExitVoucherExecution exitVoucherExecution;
    private final ListVoucherExecution listVoucherExecution;

    public VoucherExecutionMapConfiguration(CreateVoucherExecution createVoucherExecution, ExitVoucherExecution exitVoucherExecution, ListVoucherExecution listVoucherExecution) {
        this.createVoucherExecution = createVoucherExecution;
        this.exitVoucherExecution = exitVoucherExecution;
        this.listVoucherExecution = listVoucherExecution;
    }

    @Bean
    public Map<Command, VoucherExecution> commandExecutionMap() {
        EnumMap<Command, VoucherExecution> commandExecutionMap = new EnumMap<>(Command.class);
        commandExecutionMap.put(Command.CREATE, createVoucherExecution);
        commandExecutionMap.put(Command.LIST, listVoucherExecution);
        commandExecutionMap.put(Command.EXIT, exitVoucherExecution);
        return commandExecutionMap;
    }
}

