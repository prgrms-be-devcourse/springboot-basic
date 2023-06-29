package com.example.demo.voucher.presentation.command;

import com.example.demo.common.io.Input;
import com.example.demo.common.io.Output;
import com.example.demo.voucher.application.VoucherService;
import com.example.demo.voucher.application.VoucherType;
import com.example.demo.common.command.Command;
import com.example.demo.voucher.presentation.message.VoucherTypeMessage;
import com.example.demo.voucher.presentation.message.VoucherTypeMessageMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("create")
public class CreateCommand implements Command {

    private final Output output;

    private final Input input;

    private final VoucherService voucherService;

    public CreateCommand(Output output, Input input, VoucherService voucherService) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
    }

    @Override
    public void execute() {
        printMenu();
        String voucherTypeInput = input.readLine();

        VoucherType.fromCounter(voucherTypeInput).ifPresentOrElse(
                voucherType -> {
                    VoucherTypeMessage message = VoucherTypeMessageMapper.getInstance().getMessage(voucherType);
                    output.printLine(message.getMessage());
                    long value = Long.parseLong(input.readLine());
                    voucherService.createVoucher(voucherType, value);
                },
                () -> new IllegalArgumentException("Invalid Voucher Type")
        );
    }

    private void printMenu() {
        output.printLine("Please enter the voucher type:");
        Arrays.stream(VoucherType.values())
                .map(type -> type.getCounter() + " : " + VoucherTypeMessageMapper.getInstance().getMessage(type).getMessage())
                .forEach(output::printLine);
    }

}
