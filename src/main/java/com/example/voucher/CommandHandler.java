package com.example.voucher;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDto;
import com.example.voucher.ui.Input;
import com.example.voucher.ui.Output;
import com.example.voucher.util.UUIDGenerator;
import java.util.List;
import java.util.UUID;

public class CommandHandler {
    private Input input;
    private Output output;

    private UUIDGenerator uuidGenerator;

    public CommandHandler(Input input, Output output, UUIDGenerator uuidGenerator) {
        this.input = input;
        this.output = output;
        this.uuidGenerator = uuidGenerator;
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

        return new VoucherDto(uuidGenerator.generateUUID(), amount);
    }
}
