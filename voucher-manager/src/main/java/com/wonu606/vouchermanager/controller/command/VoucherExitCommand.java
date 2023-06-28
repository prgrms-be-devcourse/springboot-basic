package com.wonu606.vouchermanager.controller.command;

import com.wonu606.vouchermanager.io.ConsolePrinterView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VoucherExitCommand implements VoucherCommand {

    private final ConsolePrinterView printerView;

    @Override
    public CommandResult execute() {
        printerView.printExitMessage();
        return new CommandResult(false);
    }
}
