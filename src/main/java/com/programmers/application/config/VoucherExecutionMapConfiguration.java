package com.programmers.application.config;

import com.programmers.application.controller.console.voucher.command.VoucherCommand;
import com.programmers.application.controller.console.voucher.command.execution.CreateVoucherExecution;
import com.programmers.application.controller.console.voucher.command.execution.ExitVoucherExecution;
import com.programmers.application.controller.console.voucher.command.execution.ListVoucherExecution;
import com.programmers.application.controller.console.voucher.command.execution.VoucherExecution;
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
    public Map<VoucherCommand, VoucherExecution> voucherExecutionMap() {
        EnumMap<VoucherCommand, VoucherExecution> voucherExecutionEnumMap = new EnumMap<>(VoucherCommand.class);
        voucherExecutionEnumMap.put(VoucherCommand.CREATE, createVoucherExecution);
        voucherExecutionEnumMap.put(VoucherCommand.LIST, listVoucherExecution);
        voucherExecutionEnumMap.put(VoucherCommand.EXIT, exitVoucherExecution);
        return voucherExecutionEnumMap;
    }
}

