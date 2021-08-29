package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.command.Command;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.exception.ExceptionMessage;
import org.prgrms.kdt.exception.FormatException;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.view.CommandLineView;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;
    private final CommandLineView commandLineView = new CommandLineView();

    public VoucherController(VoucherService service) {
        this.voucherService = service;
    }

    public void start() {
        showCommandDescription();

        while (true) {
            try{
                Command cmd = getCommand();
                executeCommand(cmd);
                if (isExitCommand(cmd)) {
                    break;
                }
            }
            catch (Exception e){
                commandLineView.showErrorMessage(e.getMessage());
            }
        }
    }

    public void showCommandDescription() {
        commandLineView.showStartMessage();
    }

    public Command getCommand() {
        return Command.findByCommandName(commandLineView.requestCommand());
    }

    public void executeCommand(Command command) {
        if (command == Command.CREATE) {
            executeCreateCommand();
            return;
        }

        if (command == Command.LIST) {
            executeListCommand();
            return;
        }

        if (command == Command.EXIT) {
            executeExitCommand();
            return;
        }
    }

    public void executeCreateCommand() {
        VoucherType voucherType = VoucherType.findByVoucherType(commandLineView.requestVoucherType());

        try {
            long discount = Long.parseLong(commandLineView.requestDiscountValue());
            voucherService.addVoucher(voucherService.createVoucher(voucherType, UUID.randomUUID(), discount));
        }
        catch (NumberFormatException e){
            throw new FormatException(ExceptionMessage.NUMBER_FORMAT_EXCEPTION.getMessage());
        }

    }

    public void executeListCommand() {
        commandLineView.showVoucherList(voucherService.getAllVouchers());
    }

    private void executeExitCommand() {
        commandLineView.close();
    }

    public boolean isExitCommand(Command command) {
        return command == Command.EXIT;
    }
}
