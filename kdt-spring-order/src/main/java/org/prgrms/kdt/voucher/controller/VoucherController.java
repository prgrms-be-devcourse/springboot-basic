package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.view.cmd.Command;
import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.view.cmd.exception.DiscountNumberFormatException;
import org.prgrms.kdt.view.cmd.CommandLineView;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
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
                logger.error(e.getMessage());
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
            throw new DiscountNumberFormatException(ExceptionMessage.NUMBER_FORMAT_EXCEPTION.getMessage());
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
