package com.prgrms.springbootbasic.app;

import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;
import com.prgrms.springbootbasic.common.exception.InvalidCommandTypeException;
import com.prgrms.springbootbasic.common.exception.InvalidVoucherTypeException;
import com.prgrms.springbootbasic.console.Console;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import com.prgrms.springbootbasic.voucher.VoucherManager;
import com.prgrms.springbootbasic.voucher.VoucherType;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication {

    private final VoucherManager voucherManager;

    private final Console console;

    private final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

    private ApplicationStatus applicationStatus;

    public VoucherApplication(VoucherManager voucherManager, Console console) {
        this.voucherManager = voucherManager;
        this.console = console;
    }

    public void runLifecycle() {
        applicationStatus = new ApplicationStatus();
        logger.info("Voucher application started successfully.");
        while (applicationStatus.isRunning()) {
            getCommand();
        }
    }

    private void getCommand() {
        console.printMenu();
        String inputCommand = console.getCommand();
        try {
            CommandType commandType = CommandType.from(inputCommand);
            controlMenu(commandType);
        } catch (InvalidCommandTypeException e) {
            console.printExceptionMessage(e.getMessage());
        }
    }

    private void controlMenu(CommandType command) {
        switch (command) {
            case EXIT -> exit();
            case CREATE -> create();
            case LIST -> list();
            default -> console.printCommendNotSupported();
        }
    }

    private void exit() {
        applicationStatus.exit();
        console.printExitMessage();
        logger.info("Exit Voucher application successfully.");
    }

    private void list() {
        List<Voucher> vouchers = voucherManager.list();
        console.printVoucherList(vouchers);
        logger.info("List up all Vouchers.");
    }

    private void create() {
        try {
            VoucherType voucherType = getVoucherType();
            String amountInput = getAmount(voucherType);
            voucherManager.create(voucherType, amountInput);
            console.printCreateSuccessMessage();
            logger.info("New Voucher created.");
        } catch (InvalidVoucherTypeException | NumberFormatException | AmountOutOfBoundException e) {
            console.printExceptionMessage(e.getMessage());
        }
    }

    private VoucherType getVoucherType() {
        console.printChoosingVoucher();
        String voucherTypeInput = console.getInput();
        return VoucherType.from(voucherTypeInput);
    }

    private String getAmount(VoucherType voucherType) {
        console.printDiscountAmountMessage(voucherType);
        return console.getInput();
    }
}
