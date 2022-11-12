package org.prgrms.kdt.controller;

import org.prgrms.kdt.CommandType;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.*;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.voucher.VoucherValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CommandLineController {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineController.class);

    private final Console console;
    private final VoucherService voucherService;

    public CommandLineController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            console.printCommandList();
            try {
                CommandType commandType = CommandType.selectType(console.inputCommand());
                switch (commandType) {
                    case CREAT -> {
                        createVoucher();
                    }
                    case LIST -> {
                        List<Voucher> vouchers = voucherService.getAllVoucher();
                        console.printVouchers(vouchers);
                    }
                    case EXIT -> {
                        console.terminate();
                        running = false;
                    }
                    default -> console.printError(ErrorCode.IOEXCEPTION);
                }
            } catch (IOException e) {
                console.printError(ErrorCode.IOEXCEPTION);
                logger.error(ErrorCode.IOEXCEPTION.getMessage());
                continue;
            } catch (WrongCommand e) {
                console.printError(ErrorCode.WRONG_COMMAND);
                logger.error(ErrorCode.WRONG_COMMAND.getMessage());
                continue;
            }
        }
    }

    private void createVoucher() {
        boolean continueJob = true;
        while (continueJob) {
            try {
                String voucherType = console.inputVoucherType();
                String discountValue = console.inputVoucherDiscountValue();
                VoucherValidator.validateVoucher(voucherType, discountValue);
                voucherService.createVoucher(voucherType, Long.parseLong(discountValue));
            } catch (IsNotNumberException e) {
                console.printError(ErrorCode.IS_NOT_NUMBER);
                logger.error(ErrorCode.IS_NOT_NUMBER.getMessage());
                continue;
            } catch (WrongRangeInputException e) {
                console.printError(ErrorCode.WRONG_RANGE_INPUT);
                logger.error(ErrorCode.WRONG_RANGE_INPUT.getMessage());
                continue;
            } catch (IOException e) {
                console.printError(ErrorCode.IOEXCEPTION);
                logger.error(ErrorCode.IOEXCEPTION.getMessage());
                continue;
            } catch (NotFindVoucherType e) {
                console.printError(ErrorCode.NOT_FIND_VOUCHER_TYPE);
                logger.error(ErrorCode.NOT_FIND_VOUCHER_TYPE.getMessage());
                continue;
            }
            continueJob = false;
        }
    }
}
