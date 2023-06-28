package com.example.demo.voucher.presentation.command.impl;

import com.example.demo.common.io.Output;
import com.example.demo.voucher.application.VoucherService;
import com.example.demo.voucher.presentation.command.VoucherCommand;
import org.springframework.stereotype.Component;
@Component("list")
public class ListCommand implements VoucherCommand {

    private final Output output;

    public ListCommand(Output output) {
        this.output = output;
    }

    @Override
    public void execute(VoucherService voucherService) {
        output.printLine("Vouchers:");

        voucherService.listVouchers()
                .stream()
                .map(voucher -> voucher.getName() + ": " + voucher.getValue())
                .forEach(output::printLine);
    }
}
