package com.example.voucher;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDto;
import com.example.voucher.ui.ConsoleView;
import com.example.voucher.util.UUIDGenerator;
import java.util.List;

public class CommandHandler {
    private ConsoleView consoleView;
    private UUIDGenerator uuidGenerator;

    public CommandHandler(ConsoleView consoleView, UUIDGenerator uuidGenerator) {
        this.consoleView = consoleView;
        this.uuidGenerator = uuidGenerator;
    }

    public void showStartMessage() {
        consoleView.printProgramInfo();
    }

    public Command handleCommand() {
        String command = consoleView.readInput();
        return Command.fromString(command);
    }

    public void handleListCommand(List<Voucher> vouchers) {
        consoleView.printVoucherInfoList(vouchers);
    }

    public VoucherDto handleCreateCommand() {
        consoleView.requestVoucherAmount();
        double amount = consoleView.readVoucherInput();

        return new VoucherDto.Builder()
                .withVoucherId(uuidGenerator.generateUUID())
                .withAmount(amount)
                .build();
    }
}
