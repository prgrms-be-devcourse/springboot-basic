package com.example.demo.voucher.presentation.command;

import com.example.demo.common.io.Output;
import com.example.demo.voucher.application.VoucherService;
import com.example.demo.common.command.Command;
import org.springframework.stereotype.Component;
@Component("list")
public class ListCommand implements Command {

    private final Output output;

    private final VoucherService voucherService;

    public ListCommand(Output output, VoucherService voucherService) {
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void execute() {
        output.printLine("Vouchers:");

        voucherService.listVouchers()
                .stream()
                .map(voucher -> voucher.getName() + ": " + voucher.getValue())
                .forEach(output::printLine);
    }
}
