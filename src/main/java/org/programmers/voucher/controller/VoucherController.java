package org.programmers.voucher.controller;

import org.programmers.voucher.io.Input;
import org.programmers.voucher.io.Output;
import org.programmers.voucher.service.VoucherService;
import org.programmers.voucher.util.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;
    private final Input input;
    private final Output output;

    public VoucherController(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        output.startProgram();
        Command cmd = null;
        do {
            output.listCommand();
            try {
                cmd = parseCommand();
                logger.info("cmd => {}", cmd.toString());
            } catch (IllegalArgumentException e) {
                output.printError("Illegal Argument");
                logger.error(e.getMessage());
            }
        } while (!Command.EXIT.equals(cmd));
    }

    private Command parseCommand() {
        Command command = null;
        while (command == null) {
            try {
                command = input.inputCommand();
                logger.info("command in parse command => {}", command.toString());
            } catch (IllegalArgumentException e) {
                output.printError("Illegal Argument");
                logger.error(e.getMessage());
            }
        }
        switch (command) {
            case EXIT:
                logger.info("exit program");
                break;
            case CREATE:
                output.listVoucherType();
                voucherService.makeVoucher(input.inputVoucherType(), input.inputValue());
                logger.info("End create voucher");
                break;
            case LIST:
                output.listVoucher(voucherService.listVoucher());
                logger.info("End list vouchers");
                break;
        }
        return command;
    }
}
