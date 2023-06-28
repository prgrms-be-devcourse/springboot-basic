package com.wonu606.vouchermanager.Controller.command;

import com.wonu606.vouchermanager.io.ConsolePrinterView;

public class VoucherExitCommand implements VoucherCommand {

    private final ConsolePrinterView printerView;

    public VoucherExitCommand(ConsolePrinterView printerView) {
        this.printerView = printerView;
    }

    @Override
    public CommandResult execute() {
        printerView.printExitMessage();
        return new CommandResult(false);
    }
}
