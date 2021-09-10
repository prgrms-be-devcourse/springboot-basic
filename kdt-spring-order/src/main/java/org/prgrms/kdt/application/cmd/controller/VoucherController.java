package org.prgrms.kdt.application.cmd.controller;

import org.prgrms.kdt.application.cmd.Command;
import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.application.cmd.exception.DiscountNumberFormatException;
import org.prgrms.kdt.application.cmd.view.CommandLineView;
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
    private final CommandLineView commandLineView;

    public VoucherController(VoucherService service, CommandLineView commandLineView) {
        this.voucherService = service;
        this.commandLineView = commandLineView;
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

    private void showCommandDescription() {
        commandLineView.showStartMessage();
    }

    private Command getCommand() {
        return Command.findByCommandName(commandLineView.requestCommand());
    }

    private void executeCommand(Command command) {
        switch (command){
            case CREATE:
                executeCreateCommand();
                break;
            case LIST:
                executeListCommand();
                break;
            case EXIT:
                executeExitCommand();
                break;
            default:
                break;
        }
    }

    private void executeCreateCommand() {
        VoucherType voucherType = VoucherType.findByVoucherType(commandLineView.requestVoucherType());

        try {
            long discount = Long.parseLong(commandLineView.requestDiscountValue());
            voucherService.addVoucher(voucherService.createVoucher(voucherType, UUID.randomUUID(), discount));
        }
        catch (NumberFormatException e){
            throw new DiscountNumberFormatException(ExceptionMessage.NUMBER_FORMAT_EXCEPTION.getMessage());
        }

    }

    private void executeListCommand() {
        commandLineView.showVoucherList(voucherService.getAllVouchers());
    }

    private void executeExitCommand() {
        commandLineView.close();
        //System.exit(0);
    }

    private boolean isExitCommand(Command command) {
        return command == Command.EXIT;
    }
}
