package com.example.voucher;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDto;
import com.example.voucher.ui.Input;
import com.example.voucher.ui.Output;
import java.util.List;
import java.util.UUID;

public class CommandHandler {
    private Input input;
    private Output output;

    public CommandHandler(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public Command handleCommand() {
        String command = input.readInput();
        return Command.fromString(command);
    }

    public void handleListCommand(List<Voucher> vouchers) {
        output.printVoucherInfoList(vouchers);
    }

    public VoucherDto handleCreateCommand() {
        output.requestVoucherAmount();
        double amount = input.readVoucherInput();

        return new VoucherDto(UUID.randomUUID(), amount);
    }
}
